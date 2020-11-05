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
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_Signup extends AppCompatActivity {

    private TextView tv_login;
    private ImageView iv_back;

    private EditText ed_user_name,ed_user_email,ed_user_pass,ed_user_mobile;
    Button bt_user_signup;

    String user_id;
    String user_mobile;
    String user_password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);

        Init();
        ClickListner();
    }
    private void Init() {

        tv_login = (TextView) findViewById(R.id.tv_login);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        ed_user_name=(EditText)findViewById(R.id.ed_user_name);
        ed_user_email=(EditText)findViewById(R.id.ed_user_email);
        ed_user_pass=(EditText)findViewById(R.id.ed_user_pass);
        ed_user_mobile=(EditText)findViewById(R.id.ed_user_mobile);
        bt_user_signup=(Button)findViewById(R.id.bt_user_signup);

    }

    private void ClickListner() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_Signup.this, Merchat_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_Signup.this, Merchat_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        bt_user_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormValidation();
            }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void FormValidation() {
       if (ed_user_name.getText().toString().isEmpty())
       {
           Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
       }

       else if (ed_user_email.getText().toString().isEmpty()){
           Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
       }
       else if (!isEmailValid(ed_user_email.getText().toString())){
           Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
       }
       else if (ed_user_pass.getText().toString().isEmpty())
       {
           Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
       }

       else if (ed_user_mobile.getText().toString().isEmpty())
       {
           Toast.makeText(this, "Enter Mobile", Toast.LENGTH_SHORT).show();
       }

       else if (ed_user_mobile.getText().toString().length()<10)
       {
           Toast.makeText(this, "Enter Valid Mobile", Toast.LENGTH_SHORT).show();
       }
       else
       {
           UserSignup();
       }
    }

    private void UserSignup(){

        progressDialog = new ProgressDialog(User_Signup.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.user_signup, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        user_id = json.getString("id");
                        user_mobile=json.getString("mobile");
                        user_password=json.getString("password");
                        Toast.makeText(User_Signup.this, user_id, Toast.LENGTH_SHORT).show();
                    }

                    if (jsonObject.get("status").equals("success"))
                    {
                        Toast.makeText(User_Signup.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        gotologin();

                    }
                    else if (jsonObject.getString("status").equals("fail"))
                    {
                        Toast.makeText(User_Signup.this,  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                MyData.put("name",ed_user_name.getText().toString().trim());
                MyData.put("email",ed_user_name.getText().toString().trim());
                MyData.put("mobile",ed_user_mobile.getText().toString().trim());
                MyData.put("password",ed_user_pass.getText().toString().trim());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void gotologin() {
        Intent intent=new Intent(User_Signup.this, User_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}