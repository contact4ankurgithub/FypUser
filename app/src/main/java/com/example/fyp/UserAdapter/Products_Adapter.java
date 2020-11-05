package com.example.fyp.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fyp.Product_List;
import com.example.fyp.R;
import com.example.fyp.UserModel.Product_Model;

import java.util.ArrayList;
import java.util.List;

public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.MyViewHolder>implements Filterable {

    private List<Product_Model> product_models;
    private List<Product_Model> product_modelsFiltrable;
    private Context context;

    public Products_Adapter(Context context, List<Product_Model> product_models) {
        this.product_models = product_models;
        this.product_modelsFiltrable =product_modelsFiltrable;
        this.context = context;
         notifyItemChanged(0, product_models.size());
    }

    @NonNull
    @Override
    public Products_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_product, parent, false);
        return new Products_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Products_Adapter.MyViewHolder holder, int position) {
        final Product_Model tm = product_models.get(position);

        holder.tv_pd_price.setText(tm.getSale_price());
        holder.tv_pd_name.setText(tm.getProduct_name());
        holder.tv_pd_quantity.setText(tm.getQuantity());
        Glide.with(context).load("http://linkup.site/fyp/" + product_models.get(position).getProduct_img()).into(holder.iv_product_image);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charstring = charSequence.toString();
                if (charstring.isEmpty()) {
                    product_modelsFiltrable = product_models;
                } else {
                    List<Product_List> filtermodel = new ArrayList<>();
                    for (Product_Model request : product_models) {
                        if (request.getProduct_name().toLowerCase().contains(charstring.toLowerCase())) {
                            product_models.add(request);
                        }
                    }
                    product_modelsFiltrable = product_models;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = product_modelsFiltrable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                product_modelsFiltrable = (ArrayList<Product_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return product_models.size();
    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pd_name,tv_pd_price,tv_pd_quantity;
        ImageView iv_product_image;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pd_name = (TextView) itemView.findViewById(R.id.tv_pd_name);
            tv_pd_price = (TextView) itemView.findViewById(R.id.tv_pd_price);
            tv_pd_quantity = (TextView) itemView.findViewById(R.id.tv_pd_quantity);
            iv_product_image=(ImageView)itemView.findViewById(R.id.iv_product_image);

        }

    }
}

