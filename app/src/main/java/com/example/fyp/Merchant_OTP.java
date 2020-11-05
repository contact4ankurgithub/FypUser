package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Merchant_OTP extends AppCompatActivity {

    private Button bt_verify;
    private Pinview ed_otp_code;
    private String id;
    ProgressDialog progressDialog;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant__o_t_p);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
             id =(String) b.get("id");
          //  Textv.setText(j);
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        }

        Init();
        Onclick();
    }


    private void Init() {
        bt_verify=(Button)findViewById(R.id.bt_verify);
        ed_otp_code=(Pinview)findViewById(R.id.ed_otp_code);
    }

    private void Onclick() {
        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTP();
            }
        });
    }

    private void OTP(){
        progressDialog = new ProgressDialog(Merchant_OTP.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final String otp=  ed_otp_code.getValue().trim();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.merchant_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(Merchant_OTP.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object=new JSONObject(response);
                    if (object.get("status").equals("success"))
                    {
                        Toast.makeText(Merchant_OTP.this,  object.getString("message"), Toast.LENGTH_SHORT).show();
                        merchantdash();
                    }
                    else if (object.get("status").equals("fail"))
                    {
                        Toast.makeText(Merchant_OTP.this,  object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(GaurdRegistration.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //  Log.e("error",error.toString());
                 Toast.makeText(Merchant_OTP.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("otp",otp);
                MyData.put("id",id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void merchantdash()
    {
        Intent intent=new Intent(Merchant_OTP.this,MerchantDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}