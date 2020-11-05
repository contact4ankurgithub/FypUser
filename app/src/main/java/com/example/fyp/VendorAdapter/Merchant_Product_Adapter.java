package com.example.fyp.VendorAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fyp.VendorModel.Merchant_Product_model;
import com.example.fyp.R;

import java.util.ArrayList;
import java.util.List;

public class Merchant_Product_Adapter extends RecyclerView.Adapter<Merchant_Product_Adapter.MyVewHolder> implements Filterable {

    private List<Merchant_Product_model> productLists;
    private List<Merchant_Product_model> productListsFiltrable;
    private Context context;

    public Merchant_Product_Adapter(Context context, List<Merchant_Product_model> productLists)
    {
         this.context=context;
         this.productLists=productLists;
         this.productListsFiltrable=productLists;
         notifyItemChanged(0,productListsFiltrable.size());
    }
    @NonNull
    @Override
    public MyVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
                return new MyVewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyVewHolder holder, int position) {
        final Merchant_Product_model productList = productListsFiltrable.get(position);
        holder.tv_pd_code.setText(productList.getBarcode_id());
        holder.tv_pd_name.setText(productList.getProduct_name());
        holder.tv_pd_quantity.setText(productList.getQuantity());
        holder.tv_pd_price.setText(productList.getSale_price());

        Glide.with(context).load("http://linkup.site/fyp/"+productLists.get(position).getProduct_img()).into(holder.iv_pd_image);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListsFiltrable = productLists;
                } else {
                    List<Merchant_Product_model> filteredList = new ArrayList<>();
                    for (Merchant_Product_model request : productLists) {
                        if (request.getProduct_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(request);
                        }
                    }
                    productListsFiltrable = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListsFiltrable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListsFiltrable = (ArrayList<Merchant_Product_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return productListsFiltrable.size();
    }

    public class MyVewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pd_image;
        TextView tv_pd_code,tv_pd_name,tv_pd_quantity,tv_pd_price;
        public MyVewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pd_code=(TextView)itemView.findViewById(R.id.tv_pd_code);
            tv_pd_name=(TextView)itemView.findViewById(R.id.tv_pd_name);
            tv_pd_quantity=(TextView)itemView.findViewById(R.id.tv_pd_quantity);
            tv_pd_price=(TextView)itemView.findViewById(R.id.tv_pd_price);
            iv_pd_image=(ImageView) itemView.findViewById(R.id.iv_pd_image);


        }
    }
}
