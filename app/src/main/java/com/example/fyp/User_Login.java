package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Login extends AppCompatActivity {

    private EditText ed_user_mobile,ed_user_password;
    private TextView tv_user_signup;
    private Button bt_user_login;
    ProgressDialog progressDialog;
    String id;
    String name;
    String email;
    String mobile;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user__login);

        Init();
        ClickListner();
    }
    private void Init() {
        tv_user_signup=(TextView)findViewById(R.id.tv_user_signup);
        ed_user_mobile=(EditText)findViewById(R.id.ed_user_mobile);
        ed_user_password=(EditText)findViewById(R.id.ed_user_password);
        bt_user_login=(Button)findViewById(R.id.bt_user_login);
    }
    private void ClickListner() {

        tv_user_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_Login.this,User_Signup.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        bt_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateuserlogin();
            }
        });
    }

    private void validateuserlogin()
    {
        if (ed_user_mobile.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        }
        else if (ed_user_mobile.getText().toString().length()<10)
        {
            Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
        }
        else if (ed_user_password.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }

        else
        {
            UserLogin();
        }
    }

    private void UserLogin(){

        progressDialog = new ProgressDialog(User_Login.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.user_login, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        id = json.getString("id");
                        mobile=json.getString("mobile");
                        name=json.getString("name");
                        email=json.getString("email");
                        photo=json.getString("photo");
                       // Toast.makeText(User_Login.this, name, Toast.LENGTH_SHORT).show();
                    }

                    if (jsonObject.get("status").equals("success"))
                    {
                        Toast.makeText(User_Login.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_DETAIL", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("id", id);
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("mobile", mobile);
                        editor.putString("photo", photo);
                        editor.commit();

                        gotoUserDashboard();

                    }
                    else if (jsonObject.getString("status").equals("fail"))
                    {
                        Toast.makeText(User_Login.this,  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("mobile",ed_user_mobile.getText().toString().trim());
                MyData.put("password",ed_user_password.getText().toString().trim());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void gotoUserDashboard()
    {
        Intent intent=new Intent(User_Login.this, User_Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

