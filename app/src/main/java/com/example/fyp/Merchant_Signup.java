package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Merchant_Signup extends AppCompatActivity {
    private ImageView iv_back,iv_shop_img,iv_gst_certificate;
    private TextView tv_login;
    private EditText ed_merchant_name,ed_address,ed_businessname,ed_gstin,ed_mobile,ed_password;
    private EditText ed_holdername,ed_acc_no,ed_bankname,ed_brancename,ed_ifsccode,ed_city,ed_state;
    private Button bt_signup;
    ProgressDialog progressDialog;
    int PICK_IMAGE_REQUEST = 111;
    int PickImagerequst=112;
    String imagecertificate;
    String imageshopimage;
     Bitmap bitmap;
     String merchant_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant__signup);


        Initialization();
        ClickListner();
    }

    private void Initialization() {
        iv_back=(ImageView)findViewById(R.id.iv_back);
        tv_login=(TextView)findViewById(R.id.tv_login);
        ed_merchant_name=(EditText)findViewById(R.id.ed_merchant_name);
        ed_businessname=(EditText)findViewById(R.id.ed_businessname);
        ed_gstin=(EditText)findViewById(R.id.ed_gstin);
        ed_mobile=(EditText)findViewById(R.id.ed_mobile);
        ed_password=(EditText)findViewById(R.id.ed_password);
        ed_address=(EditText) findViewById(R.id.ed_address);
        bt_signup=(Button)findViewById(R.id.bt_signup);
        ed_holdername=(EditText) findViewById(R.id.ed_holdername);
        ed_acc_no=(EditText) findViewById(R.id.ed_acc_no);
        ed_bankname=(EditText) findViewById(R.id.ed_bankname);
        ed_brancename=(EditText) findViewById(R.id.ed_brancename);
        ed_ifsccode=(EditText) findViewById(R.id.ed_ifsccode);
        ed_city=(EditText) findViewById(R.id.ed_ifsccode);
        ed_state=(EditText) findViewById(R.id.ed_state);
        iv_gst_certificate=(ImageView)findViewById(R.id.iv_gst_certificate);
        iv_shop_img=(ImageView)findViewById(R.id.iv_shop_img);


    }

    private void ClickListner() {
      iv_back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onBackPressed();
          }
      });


      tv_login.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("ResourceAsColor")
          @Override
          public void onClick(View view) {

              Intent intent=new Intent(Merchant_Signup.this, Merchat_Login.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
          }
      });


        iv_gst_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        iv_shop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PickImagerequst);
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
               ValidateForm();
            }
        });
    }

    private void ValidateForm() {

        if (ed_businessname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Business name", Toast.LENGTH_SHORT).show();
        }

        else if (ed_gstin.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter GST", Toast.LENGTH_SHORT).show();
        }

        else if (ed_mobile.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Mobile", Toast.LENGTH_SHORT).show();
        }

        else if (ed_mobile.getText().toString().length()<10){
            Toast.makeText(getApplicationContext(), "Enter Valid Mobile", Toast.LENGTH_SHORT).show();
        }

        else if (ed_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        }

        else if (ed_address.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Address", Toast.LENGTH_SHORT).show();
        }
        else {
            merchant_signup();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri imgUri = data.getData();
            Glide.with(getApplicationContext()).load(imgUri).into(iv_gst_certificate);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                imagecertificate = convertBitmapToBase64(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage() + "  error message", Toast.LENGTH_SHORT).show();
            }
        }
        else {
          //  Toast.makeText(this, "error in on activityresult", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PickImagerequst && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri imgUri = data.getData();
            Glide.with(getApplicationContext()).load(imgUri).into(iv_shop_img);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                imageshopimage = convertBitmapToBase64(bitmap);

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage() + "  error message", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //  Toast.makeText(this, "error in on activityresult", Toast.LENGTH_SHORT).show();
        }

     //   merchant_signup();

    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        //Toast.makeText(this, Base64.encodeToString(byteArray, Base64.DEFAULT).toString() + "", Toast.LENGTH_SHORT).show();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void merchant_signup(){

        progressDialog = new ProgressDialog(Merchant_Signup.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST,BaseUrl.merchant_reg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(Merchant_Signup.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject =new JSONObject(response);

                    if (jsonObject.get("status").equals("success"))
                    {
                        merchant_id= jsonObject.getString("data");
                        user_otp();
                    }
                    else if (jsonObject.getString("status").equals("error"))
                    {
                        Toast.makeText(Merchant_Signup.this,  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Merchant_Signup.this, "errror"+error, Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name",ed_merchant_name.getText().toString().trim());
                MyData.put("business_name",ed_businessname.getText().toString().trim());
                MyData.put("gstn",ed_gstin.getText().toString().trim());
                MyData.put("mobile",ed_mobile.getText().toString().trim());
                MyData.put("password",ed_password.getText().toString().trim());
                MyData.put("address",ed_address.getText().toString().trim());
                MyData.put("shop_img",imageshopimage);
                MyData.put("certificate","null");



                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    private void user_otp()
    {
        Intent ii=new Intent(Merchant_Signup.this, Merchant_OTP.class);
        ii.putExtra("id", merchant_id);
        startActivity(ii);
    }

}
