package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

public class Bottom_function {


    private RelativeLayout rlt_shoplist;
    private RelativeLayout rlt_order;
    private RelativeLayout rlt_profile;

    public void initializeSlider(View view, Context context) {

        rlt_shoplist = (RelativeLayout) view.findViewById(R.id.rlt_shoplist);
        rlt_order = (RelativeLayout) view.findViewById(R.id.rlt_order);
        rlt_profile = (RelativeLayout) view.findViewById(R.id.rlt_profile);


    }

    public void getSlidingMenu(final Context context) {

        rlt_shoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Shop_List.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        rlt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Order.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        rlt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }


}