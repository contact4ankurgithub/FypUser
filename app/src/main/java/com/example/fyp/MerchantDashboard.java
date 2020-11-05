package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MerchantDashboard extends AppCompatActivity {

    private LinearLayout lr_add_product,lr_orderprocess;
    private ImageView logout_button;
    String userid;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_dashboard);



            Initialization();
            MySharedData();
            ClickListner();

        }


        private void Initialization() {
            lr_add_product=(LinearLayout)findViewById(R.id.lr_add_product);
            lr_orderprocess=(LinearLayout)findViewById(R.id.lr_orderprocess);
            logout_button=(ImageView)findViewById(R.id.logout_button);
        }

        private void MySharedData() {

            SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            userid = prefs.getString("id", "");
            mobile = prefs.getString("mobile","");


        }

        private void ClickListner() {

            lr_add_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MerchantDashboard.this, Add_Product.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            lr_orderprocess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MerchantDashboard.this, Orders.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            logout_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(MerchantDashboard.this, Merchat_Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

        }

}