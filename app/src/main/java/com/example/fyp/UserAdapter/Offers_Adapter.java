package com.example.fyp.UserAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.UserModel.Offer_Model;

import java.util.List;


public class Offers_Adapter extends  RecyclerView.Adapter<Offers_Adapter.MyViewHolder> {

    private List<Offer_Model> offer_models;
    private Context context;

    public Offers_Adapter(Context context, List<Offer_Model> offer_models) {
        this.offer_models = offer_models;
        this.context = context;
        //  notifyItemChanged(0, trendingPdModelsfiltrable.size());
    }

    @NonNull
    @Override
    public Offers_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new Offers_Adapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Offers_Adapter.MyViewHolder holder, int position) {
        final Offer_Model store = offer_models.get(position);
        holder.tv_offer_name.setText(store.getTitle());
    }

    @Override
    public int getItemCount() {
        return offer_models.size();
    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_offer_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_offer_name = (TextView) itemView.findViewById(R.id.tv_offer_name);

        }

    }
}

