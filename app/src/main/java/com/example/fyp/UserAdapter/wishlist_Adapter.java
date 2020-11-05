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

import com.example.fyp.R;
import com.example.fyp.UserModel.Product_Model;
import com.example.fyp.UserModel.Wishlist_Model;

import java.util.ArrayList;
import java.util.List;

public class wishlist_Adapter extends RecyclerView.Adapter<wishlist_Adapter.MyViewHolder>implements Filterable {

    private List<Wishlist_Model>wishlist_models;
    private List<Wishlist_Model>wishlist_modelsFiltrable;
    private Context context;

    public wishlist_Adapter(List<Wishlist_Model> wishlist_models, Context context) {
        this.wishlist_models = wishlist_models;
        this.wishlist_modelsFiltrable =wishlist_modelsFiltrable ;
        this.context = context;
        //notifyItemChanged(0, offersModelsFiltrable.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_pd_wishlist,parent,false);
              return new  MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          final Wishlist_Model vm=wishlist_models.get(position);
          holder.tv_pd_name.setText(vm.getProduct_name());
          holder.tv_pd_price.setText(vm.getProduct_price());
          holder.tv_pd_quantity.setText(vm.getProduct_quantity());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charstring = charSequence.toString();
                if (charstring.isEmpty()) {
                    wishlist_modelsFiltrable = wishlist_models;
                } else {
                    List<Wishlist_Model> filtermodel = new ArrayList<>();
                    for (Wishlist_Model request : wishlist_models) {
                        if (request.getProduct_name().toLowerCase().contains(charstring.toLowerCase())){
                        filtermodel.add(request);
                    }
                }
                wishlist_modelsFiltrable = wishlist_models;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values =wishlist_modelsFiltrable;
                return filterResults;
        }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                wishlist_modelsFiltrable = (ArrayList<Wishlist_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return wishlist_models.size();
    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pd_name,tv_pd_price,tv_pd_quantity;
        ImageView offer_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pd_name=(TextView)itemView.findViewById(R.id.tv_pd_name);
            tv_pd_price=(TextView) itemView.findViewById(R.id.tv_pd_price);
            tv_pd_quantity=(TextView) itemView.findViewById(R.id.tv_pd_quantity);


        }
    }
}
