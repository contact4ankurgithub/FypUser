package com.example.fyp.UserAdapter;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fyp.MainActivity;
import com.example.fyp.Product_List;
import com.example.fyp.R;
import com.example.fyp.UserModel.ShopListModel;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends  RecyclerView.Adapter<ShopListAdapter.MyViewHolder>implements Filterable {

        private List<ShopListModel> shopListModelList;
        private List<ShopListModel> shopListModelListFiltrable;
        private Context context;

        public ShopListAdapter(Context context, List<ShopListModel> pdCategoryModels) {
                this.shopListModelList = pdCategoryModels;
                this.shopListModelListFiltrable = shopListModelListFiltrable;
                this.context = context;
                //  notifyItemChanged(0, trendingPdModelsfiltrable.size());
        }

        @NonNull
        @Override
        public ShopListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_shop_list, parent, false);
                return new ShopListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopListAdapter.MyViewHolder holder, int position) {
                final ShopListModel tm = shopListModelList.get(position);
                holder.tv_rating.setText(tm.getRate());
                holder.tv_business_name.setText(tm.getBusiness_name());
                holder.tv_address.setText(tm.getAddress());
                holder.tv_ownername.setText(tm.getName());
                Glide.with(context).load("http://linkup.site/fyp/" + shopListModelList.get(position).getShop_img()).into(holder.iv_shop_img);
                holder.cv_shoplist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent=new Intent(context, Product_List.class);
                                intent.putExtra("id",tm.getId());
                                context.startActivity(intent);
                        }
                });


        }

        @Override
        public Filter getFilter() {
                return new Filter() {
                        @Override
                        protected FilterResults performFiltering(CharSequence charSequence) {
                                String charstring = charSequence.toString();
                                if (charstring.isEmpty()) {
                                        shopListModelListFiltrable = shopListModelList;
                                } else {
                                        List<ShopListModel> filtermodel = new ArrayList<>();
                                        for (ShopListModel request : shopListModelList) {
                                                if (request.getBusiness_name().toLowerCase().contains(charstring.toLowerCase())) {
                                                        shopListModelList.add(request);
                                                }
                                        }
                                        shopListModelListFiltrable = shopListModelList;
                                }

                                FilterResults filterResults = new FilterResults();
                                filterResults.values = shopListModelListFiltrable;
                                return filterResults;
                        }

                        @Override
                        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                                shopListModelListFiltrable = (ArrayList<ShopListModel>) filterResults.values;
                                notifyDataSetChanged();
                        }
                };
        }

        @Override
        public int getItemCount() {
                return shopListModelList.size();
        }

        public class
        MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_business_name,tv_rating,tv_address,tv_ownername;
                ImageView iv_shop_img;
                CardView cv_shoplist;


                public MyViewHolder(@NonNull View itemView) {
                        super(itemView);
                        tv_address = (TextView) itemView.findViewById(R.id.tv_address);
                        tv_business_name = (TextView) itemView.findViewById(R.id.tv_business_name);
                        tv_rating = (TextView) itemView.findViewById(R.id.tv_rating);
                        tv_ownername = (TextView) itemView.findViewById(R.id.tv_ownername);
                        iv_shop_img=(ImageView)itemView.findViewById(R.id.iv_shop_img);
                        cv_shoplist=(CardView)itemView.findViewById(R.id.cv_shoplist);


                }

        }
}

