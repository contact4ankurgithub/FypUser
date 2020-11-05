package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    String merchatid;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        merchatid=prefs.getString("id","");
       // Toast.makeText(this,userid , Toast.LENGTH_SHORT).show();


        Splash();
    }

    private void Splash() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               Intent intent=new Intent(Splash.this,User_Dashboard.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);

//               if (merchatid!="")
//               {
//                   Intent intent=new Intent(Splash.this,MerchantDashboard.class);
//                   startActivity(intent);
//               }
//               else{
//                   Intent intent=new Intent(Splash.this,Merchat_Login.class);
//                   startActivity(intent);
//               }

           }
       }, 1000);

    }


}