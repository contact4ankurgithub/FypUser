package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserOrMerchant extends AppCompatActivity {

    private Button bt_login_user, bt_merchant_login;
    String id_user;
    String merchatid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_or_merchant);



        Init();
        setSharedData();
        ClickListner();
    }

    private void Init() {
        bt_login_user = (Button) findViewById(R.id.bt_login_user);
        bt_merchant_login = (Button) findViewById(R.id.bt_merchant_login);
    }

    private void setSharedData()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_DETAIL", 0); // 0 - for private mode
        id_user= pref.getString("id", "");
        // Toast.makeText(this, id_user, Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        merchatid=prefs.getString("id","");
       // Toast.makeText(this,merchatid , Toast.LENGTH_SHORT).show();


    }

    private void ClickListner() {

        bt_login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(getApplicationContext(), User_Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }
        });

        bt_merchant_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MerchantDashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}