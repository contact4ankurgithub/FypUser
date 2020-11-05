package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class Orders extends AppCompatActivity {

    LinearLayout liner_inflatearchi;
    //Recyclerview
    RecyclerView rcvarchilistrecycler;
    //GridLayoutManager
    GridLayoutManager mGridLayoutManager;
    //Adapter
    Adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        MyToolbar();
        findViewById();
    }


    private void MyToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order In Process");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void findViewById() {
        rcvarchilistrecycler=(RecyclerView)findViewById(R.id.archilistrecycler);

        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        adapter = new Adapter();
        rcvarchilistrecycler.setLayoutManager(mGridLayoutManager);
        rcvarchilistrecycler.setAdapter(adapter);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rcvarchilistrecycler.setLayoutManager(layoutManager);
        //rvAllPostList.setNestedScrollingEnabled( true );
    }
    private class Adapter extends RecyclerView.Adapter<Holder> {



        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        /*public Adapter(ArrayList<HashMap<String, String>> favList) {
            this.data = favList;
        }*/

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
            Holder holder = new Holder(view);
            holder.setIsRecyclable(false);
            return holder;

            //return new Holder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.inflate_masjid_list, parent, false ) );
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Holder holder, final int position) {
//            holder.liner_inflatearchi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getApplicationContext(), ArchitechDetails.class) );
//                }
//            });



        }

        public int getItemCount() {
            // return data.size();
            return 5;
        }
    }
    private class Holder extends RecyclerView.ViewHolder {
        LinearLayout  liner_inflatearchi;


        public Holder(View itemView) {
            super(itemView);

        }
    }

}