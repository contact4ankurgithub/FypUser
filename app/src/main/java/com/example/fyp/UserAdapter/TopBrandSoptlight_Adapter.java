package com.example.fyp.UserAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fyp.BaseUrl;
import com.example.fyp.R;
import com.example.fyp.UserModel.Offer_Model;
import com.example.fyp.UserModel.TopBrandSpotlight_Model;

import java.util.List;


public class TopBrandSoptlight_Adapter extends  RecyclerView.Adapter<TopBrandSoptlight_Adapter.MyViewHolder> {

    private List<TopBrandSpotlight_Model> topBrandSpotlight_models;
    private Context context;

    public TopBrandSoptlight_Adapter(Context context, List<TopBrandSpotlight_Model> topBrandSpotlight_models) {
        this.topBrandSpotlight_models = topBrandSpotlight_models;
        this.context = context;
        //  notifyItemChanged(0, trendingPdModelsfiltrable.size());
    }

    @NonNull
    @Override
    public TopBrandSoptlight_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_brand_spotlight, parent, false);
        return new TopBrandSoptlight_Adapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TopBrandSoptlight_Adapter.MyViewHolder holder, int position) {
        final TopBrandSpotlight_Model store = topBrandSpotlight_models.get(position);
        holder.tv_brand_name.setText(store.getBrand_name());
        Glide.with(context).load(BaseUrl.topbrandimage +topBrandSpotlight_models.get(position).getBrand_img()).into(holder.iv_brand_img);

    }

    @Override
    public int getItemCount() {
        return topBrandSpotlight_models.size();
    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_brand_name;
        ImageView iv_brand_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_brand_name = (TextView) itemView.findViewById(R.id.tv_brand_name);
            iv_brand_img = (ImageView) itemView.findViewById(R.id.iv_brand_img);
        }

    }
}

