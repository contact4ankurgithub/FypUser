package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.fyp.VendorModel.Categorymodel;
import com.example.fyp.VendorModel.SubCategorymodel;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Barcode_Scanner extends AppCompatActivity implements View.OnClickListener {

    private ImageView scanBtn,img_product_image;
    private Button bt_add_new_product;
    private CheckBox cb_trend;
    private TextView tv_category,tv_sub_cat,tv_br_id,tv_br_name,ed_pd_quantity,ed_pd_price,ed_pd_discount;
    private EditText ed_pd_code,ed_pd_name;
    private LinearLayout llSearch,lr_tv_set_cat_sub,lr_sp_set_cat_sub,lr_ed_barcodenam_id,lr_tv_barcodenm_id;
    ProgressDialog progressDialog;
    String barcodeid;
    String productname;
    String productcode;
    String productimage;
    String product_cat;
    String product_sub_cat;
    String merchant_id;

   private ArrayList<String>mresult;
   String checkbox;

    List<Categorymodel> categorymodel;
    List<SubCategorymodel> subCategorymodels;
    Spinner sp_main_category,sp_subcategory;
    ArrayList<String> categoryName;
    ArrayList<String> subcategoryName;

    String categoryid;
    String cid;
    String subid;

    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;
    String imageString;

    private static final String TAG = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode__scanner);


        categoryName=new ArrayList<>();
        subcategoryName=new ArrayList<>();


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        merchant_id=prefs.getString("id","");
        Toast.makeText(this,merchant_id , Toast.LENGTH_SHORT).show();



        scanBtn = (ImageView) findViewById(R.id.scan_button);
        bt_add_new_product=(Button)findViewById(R.id.bt_add_new_product);
        img_product_image=(ImageView)findViewById(R.id.img_product_image);
        cb_trend=(CheckBox)findViewById(R.id.cb_trend);
        ed_pd_code=(EditText)findViewById(R.id.ed_pd_code);
        ed_pd_name=(EditText)findViewById(R.id.ed_pd_name);
        ed_pd_quantity=(EditText)findViewById(R.id.ed_pd_quantity);
        ed_pd_price=(EditText)findViewById(R.id.ed_pd_price);
        ed_pd_discount=(EditText)findViewById(R.id.ed_pd_discount);
        tv_category=(TextView)findViewById(R.id.tv_category);
        tv_sub_cat=(TextView)findViewById(R.id.tv_sub_cat);
        tv_br_name=(TextView)findViewById(R.id.tv_br_name);
        tv_br_id=(TextView)findViewById(R.id.tv_br_id);

        sp_main_category=(Spinner)findViewById(R.id.sp_main_category);
        sp_subcategory=(Spinner)findViewById(R.id.sp_subcategory);

        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        lr_tv_set_cat_sub=(LinearLayout)findViewById(R.id.lr_tv_set_cat_sub);
        lr_sp_set_cat_sub=(LinearLayout)findViewById(R.id.lr_sp_set_cat_sub);
        lr_ed_barcodenam_id=(LinearLayout)findViewById(R.id.lr_ed_barcodenam_id);
        lr_tv_barcodenm_id=(LinearLayout)findViewById(R.id.lr_tv_barcodenm_id);

        scanBtn.setOnClickListener(this);


        Onclick();
        category_id();
        GetCategory();
        subcategory_data();

    }

    @Override
    public void onClick(View view) {
        llSearch.setVisibility(View.GONE);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a barcode or QRcode");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imgUri = data.getData();
            Glide.with(getApplicationContext()).load(imgUri).into(img_product_image);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                imageString = convertBitmapToBase64(bitmap);
               // Toast.makeText(this, imageString + "  converter mehtod", Toast.LENGTH_SHORT).show();
                validateform();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage() + "  error message", Toast.LENGTH_SHORT).show();
            }

        } else {
           // Toast.makeText(this, "error in on activityresult", Toast.LENGTH_SHORT).show();
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                llSearch.setVisibility(View.GONE);
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }

            else
                {
                llSearch.setVisibility(View.VISIBLE);
                barcodeid =result.getContents();
                CheckData();
            }
        }

        else

            {
            super.onActivityResult(requestCode, resultCode, data);
        }

//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//
//            try {
//                //getting image from gallery
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//                //Setting image to ImageView
//                img_product_image.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        //Toast.makeText(this, Base64.encodeToString(byteArray, Base64.DEFAULT).toString() + "", Toast.LENGTH_SHORT).show();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void category_id() {
        sp_main_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int getid = adapterView.getSelectedItemPosition();   ///get the selected element place id
                sp_main_category.getSelectedItemId();

                for (int k=0;k<categoryName.size();k++)
                {
                    if (k == getid)
                    {
                        cid=categorymodel.get(k).getId();
                        loadsubcategory(cid);
                        break;
                    }
                   // Toast.makeText(Barcode_Scanner.this,"cid"+cid, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void GetCategory() {
        progressDialog = new ProgressDialog(Barcode_Scanner.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, BaseUrl.category_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                categorymodel=new ArrayList<>();
                categoryName=new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("success")){

                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String categoryname=jsonObject1.getString("name");
                            categoryid=jsonObject1.getString("id");
                            // Toast.makeText(MainActivity.this, categoryid, Toast.LENGTH_SHORT).show();
                            categoryName.add(categoryname);

                            Categorymodel model=new Categorymodel();
                            model.setId(jsonObject1.getString("id"));
                            model.setName(jsonObject1.getString("name"));
                            categorymodel.add(model);
                        }
                    }

                    sp_main_category.setAdapter(new ArrayAdapter<String>(Barcode_Scanner.this, android.R.layout.simple_spinner_dropdown_item, categoryName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(Barcode_Scanner.this, "eroor"+error, Toast.LENGTH_SHORT).show();
            }
        });
        //   int socketTimeout = 30000;
        // RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void subcategory_data(){
        sp_subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int getsubid = adapterView.getSelectedItemPosition();   ///get the selected element place id
                sp_subcategory.getSelectedItemId();

                for (int k=0;k<subcategoryName.size();k++)
                {
                    if (k == getsubid)
                    {
                        subid=subCategorymodels.get(k).getSub_id();
                        //   loadsubcategory(cid);
                        break;
                    }
                  //  Toast.makeText(Barcode_Scanner.this,"sub"+subid, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void loadsubcategory(final String id) {
        progressDialog = new ProgressDialog(Barcode_Scanner.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST,BaseUrl.subcategory_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                subCategorymodels=new ArrayList<>();
                subcategoryName=new ArrayList<>();
                // Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("success")){

                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String mainname=jsonObject1.getString("name");
                            categoryid=jsonObject1.getString("id");

                            SubCategorymodel submodel=new SubCategorymodel();
                            submodel.setSub_id(jsonObject1.getString("id"));
                            submodel.setSub_name(jsonObject1.getString("name"));
                            subCategorymodels.add(submodel);

                            //   Toast.makeText(MainActivity.this, categoryid, Toast.LENGTH_SHORT).show();
                            subcategoryName.add(mainname);
                        }
                    }
                    sp_subcategory.setAdapter(new ArrayAdapter<String>(Barcode_Scanner.this, android.R.layout.simple_spinner_dropdown_item, subcategoryName));
                }catch (JSONException e){e.printStackTrace();}

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Barcode_Scanner.this, "errr" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id",id);
                return MyData;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        MyStringRequest.setRetryPolicy(policy);
        MyRequestQueue.add(MyStringRequest);
    }

    private void CheckData() {

        progressDialog = new ProgressDialog(Barcode_Scanner.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.barcode_scan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               // Toast.makeText(Barcode_Scanner.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        productname = json.getString("product_name");
                        productcode=json.getString("barcode_id");

                        productimage = json.getString("product_img");
                        product_cat=json.getString("main_cat_name");
                        product_sub_cat=json.getString("sub_cat_name");

                        productcode=json.getString("merchant_id");
                        productcode=json.getString("main_id");
                        productcode=json.getString("sub_id");
                        productcode=json.getString("sale_price");
                        productcode=json.getString("discount");
                        productcode=json.getString("quantity");

                       // Toast.makeText(Barcode_Scanner.this, productname, Toast.LENGTH_SHORT).show();
                    }

                    if (jsonObject.get("status").equals("success"))
                    {
                        Toast.makeText(Barcode_Scanner.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        fillform();
                    }
                    else if (jsonObject.getString("status").equals("fail"))
                    {
                        MDToast mdToast = MDToast.makeText(Barcode_Scanner.this, jsonObject.getString("message"),3,MDToast.TYPE_INFO);
                        mdToast.setGravity(Gravity.CENTER, 0, 0);
                        mdToast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Barcode_Scanner.this, "errr"+error, Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("barcode", barcodeid);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);


    }

    private void fillform() {
        lr_sp_set_cat_sub.setVisibility(View.GONE);
        lr_tv_set_cat_sub.setVisibility(View.VISIBLE);
        lr_ed_barcodenam_id.setVisibility(View.GONE);
        lr_tv_barcodenm_id.setVisibility(View.VISIBLE);
        tv_br_id.setText(barcodeid);
        tv_br_name.setText(productname);
        tv_category.setText(product_cat);
        tv_sub_cat.setText(product_sub_cat);
        Picasso.with(getApplicationContext()).load(BaseUrl.product_img).into(img_product_image);


    }

    private void Onclick() {
        img_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        bt_add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateform();
            }
        });

        cb_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 mresult= new ArrayList<>();
                StringBuffer sb = new StringBuffer();

                if (cb_trend.isChecked())
                {
                    mresult.add("Trending");
                  //  Log.d(TAG, String.valueOf(mresult));
                  //  Toast.makeText(Barcode_Scanner.this, ""+mresult, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mresult.add("");
                 //   Toast.makeText(Barcode_Scanner.this, ""+mresult, Toast.LENGTH_SHORT).show();
                }

                for (String s : mresult) {
                    sb.append(s);
                    sb.append(" ");
                }
                 checkbox = sb.toString();

                Toast.makeText(Barcode_Scanner.this, ""+checkbox, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateform() {



         if (ed_pd_code.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Barcode Id", Toast.LENGTH_SHORT).show();
        }
        else if (ed_pd_name.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Product Name", Toast.LENGTH_SHORT).show();
        }
        else if (sp_main_category.getSelectedItem().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Pick Category", Toast.LENGTH_SHORT).show();
        }
        else if (sp_subcategory.getSelectedItem().toString().trim().equals("pick subcategory"))
        {
            Toast.makeText(this, "Pick Subcategory", Toast.LENGTH_SHORT).show();
        }
        else if (ed_pd_quantity.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Quantity", Toast.LENGTH_SHORT).show();
        }
        else if (ed_pd_price.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show();
        }

        else if (imageString.equals(""))
        {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }

        else {
            whendatanull();
        }

    }

    private void whendatanull() {
        progressDialog = new ProgressDialog(Barcode_Scanner.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.merchant_add_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                 Toast.makeText(Barcode_Scanner.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject =new JSONObject(response);

                    if (jsonObject.get("status").equals("success"))
                    {
                        Toast.makeText(Barcode_Scanner.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if (jsonObject.getString("status").equals("fail"))
                    {
                        Toast.makeText(Barcode_Scanner.this,  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Barcode_Scanner.this, "errr"+error, Toast.LENGTH_SHORT).show();

            }
        })

        {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("barcode_id",ed_pd_code.getText().toString());
                MyData.put("product_name",ed_pd_name.getText().toString() );
                MyData.put("merchant_id",merchant_id );
                MyData.put("main_id",cid );
                MyData.put("sub_id",subid );
                MyData.put("sale_price",ed_pd_price.getText().toString() );
                MyData.put("discount",ed_pd_discount.getText().toString() );
                MyData.put("quantity",ed_pd_quantity.getText().toString() );
                MyData.put("tag",checkbox);
                MyData.put("product_img",imageString);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);


    }


}