package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private TextView tv_user_name,tv_user_email,tv_user_mobile;
    private ImageView iv_user_profile;
    String username;
    Button bt_user_logout;
    String id_user;
    String user_name;
    String user_email;
    String user_mobile;
    String user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        MyToolbar();
        Init();
        MySharedData();
        userData();
        ClickListner();

    }



    private void MyToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Init() {

         bt_user_logout=(Button)findViewById(R.id.bt_user_logout);
        iv_user_profile=(ImageView)findViewById(R.id.iv_user_profile);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_user_email=(TextView)findViewById(R.id.tv_user_email);
        tv_user_mobile=(TextView)findViewById(R.id.tv_user_mobile);

    }


    private void MySharedData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_DETAIL", 0); // 0 - for private mode
        id_user= pref.getString("id", "");
        user_name= pref.getString("name", "");
        user_email= pref.getString("email", "");
        user_photo= pref.getString("photo", "");
        user_mobile= pref.getString("mobile", "");
       // Toast.mak     eText(this, id_user, Toast.LENGTH_SHORT).show();


    }

    private void userData()
    {
        tv_user_name.setText(username);
        tv_user_email.setText(user_email);
        tv_user_mobile.setText(user_mobile);
    }

    private void ClickListner() {


      bt_user_logout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_DETAIL", 0); // 0 - for private mode
              SharedPreferences.Editor editor = pref.edit();
              editor.clear();
              editor.apply();
              Intent intent = new Intent(Profile.this, User_Login.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
          }
      });
    }


}