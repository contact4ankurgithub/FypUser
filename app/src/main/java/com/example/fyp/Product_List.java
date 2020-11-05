package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.UserAdapter.Products_Adapter;
import com.example.fyp.UserModel.Product_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_List extends AppCompatActivity {
    private List<Product_Model> product_modelslist;
    private Products_Adapter productsAdapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    RecyclerView rv_prduct_list;
    SwipeRefreshLayout swipeLayout;
    ProgressDialog progressDialog;
    String vendor_id;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product__list);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            vendor_id =(String) b.get("id");
           // Textv.setText(j);
            Toast.makeText(this, vendor_id, Toast.LENGTH_SHORT).show();
        }


        MyToolbar();
        Product_list_Recycler();
        Get_ProductList();
        Refresh();
    }

    private void MyToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Refresh() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(Color.RED, Color.MAGENTA, Color.GREEN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Get_ProductList();
                        progressDialog.dismiss();
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Product_list_Recycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_prduct_list = (RecyclerView) findViewById(R.id.rv_prduct_list);
        rv_prduct_list.setLayoutManager(layoutManager);
        product_modelslist = new ArrayList<>();
        productsAdapter = new Products_Adapter(this, product_modelslist);
        rv_prduct_list.setAdapter(productsAdapter);
    }

    private void Get_ProductList(){
        progressDialog = new ProgressDialog(Product_List.this);
        progressDialog.setMessage("Please wait...");
      //  progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.productlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                SetCategoryResponse(response);
               //  Toast.makeText(Product_List.this, response, Toast.LENGTH_SHORT).show();
            }


        }, new Response.ErrorListener() {
            //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
               // Toast.makeText(Product_List.this, "error"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id",vendor_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void SetCategoryResponse(String response) {

        try {

            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("status").equals("success")){
                MDToast mdToast = MDToast.makeText(Product_List.this,jsonObject.getString("message"),2,MDToast.TYPE_SUCCESS);
                mdToast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                mdToast.show();
                JSONArray jsonarray = new JSONArray(jsonObject.getString("data"));

                Gson gson=new Gson();
                Type listType=new TypeToken<List<Product_Model>>(){}.getType();
                List<Product_Model> items=gson.fromJson(jsonarray.toString(),listType);
                product_modelslist.clear();
                product_modelslist.addAll(items);
                productsAdapter.notifyDataSetChanged();

            }
            else if(jsonObject.get("status").equals("fail")){
                MDToast mdToast = MDToast.makeText(Product_List.this, jsonObject.getString("message"),3,MDToast.TYPE_ERROR);
                mdToast.setGravity(Gravity.CENTER, 0, 0);
                mdToast.show();

            }

        }
        catch (JSONException e) { e.printStackTrace(); }
    }

}