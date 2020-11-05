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

public class Merchat_Login extends AppCompatActivity {

    private TextView tv_signup;
    private ImageView iv_back;
    private EditText ed_mobile,ed_password;
    private Button bt_login;
    ProgressDialog progressDialog;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_login);


        init();
        ClickListner();
    }


    private void init() {
        tv_signup=(TextView)findViewById(R.id.tv_signup);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        ed_mobile=(EditText)findViewById(R.id.ed_mobile);
        ed_password=(EditText)findViewById(R.id.ed_password);
        bt_login=(Button)findViewById(R.id.bt_login);

    }

    private void ClickListner() {
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Merchat_Login.this, Merchant_Signup.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(Merchat_Login.this, Merchant_OTP.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                Validate_merchant_Login();
            }
        });

    }

    private void Validate_merchant_Login() {

        if (ed_mobile.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Mobile", Toast.LENGTH_SHORT).show();
        }

        else if (ed_mobile.getText().toString().length()<10){
            Toast.makeText(getApplicationContext(), "Enter Valid Mobile", Toast.LENGTH_SHORT).show();
        }

        else if (ed_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Valid Mobile", Toast.LENGTH_SHORT).show();
        }
        else {
            Merchat_Login();
        }

    }

    private void Merchat_Login(){

        progressDialog = new ProgressDialog(Merchat_Login.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.merchant_login, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        userid = json.getString("id");
                        String mobile=json.getString("mobile");
                        Toast.makeText(Merchat_Login.this, userid, Toast.LENGTH_SHORT).show();
                    }

                    if (jsonObject.get("status").equals("success"))
                    {
                        Toast.makeText(Merchat_Login.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                        editor.putString("id",userid);
                        editor.apply();
                        goto_merchat_dashboard();

                    }
                    else if (jsonObject.getString("status").equals("fail"))
                    {
                        Toast.makeText(Merchat_Login.this,  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                MyData.put("mobile",ed_mobile.getText().toString().trim());
                MyData.put("password",ed_password.getText().toString().trim());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void goto_merchat_dashboard()
    {
        Intent intent=new Intent(Merchat_Login.this,MerchantDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}