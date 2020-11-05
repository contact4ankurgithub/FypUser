package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.VendorAdapter.Merchant_Product_Adapter;
import com.example.fyp.VendorModel.Merchant_Product_model;
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

public class Add_Product extends AppCompatActivity  {


    private AlertDialog dialog;
    private static final int REQUEST_CAMERA=1;
    private ImageView iv_add_product,img_barcodescan;

    private List<Merchant_Product_model> merchantProduct_model;
    private Merchant_Product_Adapter merchantProduct_adapter;
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    RecyclerView recyclerView;
    Intent intent;
    SwipeRefreshLayout swipeLayout;
    String merchant_id;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add__product);



        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        merchant_id=prefs.getString("id","");
       // Toast.makeText(this,merchant_id , Toast.LENGTH_SHORT).show();


        MyToolbar();
        Initialization();
        ClickListner();
        Search();
        Refresh();
        MyRecyclerView();
        addeddata();

    }

    private void MyToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Product");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void Initialization() {
        iv_add_product=(ImageView)findViewById(R.id.iv_add_product);
    }



    private void ClickListner() {

        iv_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Barcode_Scanner.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void Search() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("All Added Product");
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.clearFocus();
        searchView.setQueryHint("Type to search..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                merchantProduct_adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                merchantProduct_adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
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
                        addeddata();
                        progressDialog.dismiss();
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void MyRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        merchantProduct_model = new ArrayList<>();
        merchantProduct_adapter = new Merchant_Product_Adapter(this, merchantProduct_model);
        recyclerView.setAdapter(merchantProduct_adapter);
    }

    private void addeddata(){

        progressDialog = new ProgressDialog(Add_Product.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.AllproductMerchant, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                SetResponse(response);
              //  Toast.makeText(Add_Product.this, response, Toast.LENGTH_SHORT).show();
                }


        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id",merchant_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void SetResponse(String response) {

        try {

            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("status").equals("success")){
                MDToast mdToast = MDToast.makeText(Add_Product.this,jsonObject.getString("message"),2,MDToast.TYPE_SUCCESS);
                mdToast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                mdToast.show();
                JSONArray jsonarray = new JSONArray(jsonObject.getString("data"));

                Gson gson=new Gson();
                Type listType=new TypeToken<List<Merchant_Product_model>>(){}.getType();
                List<Merchant_Product_model> items=gson.fromJson(jsonarray.toString(),listType);
                merchantProduct_model.clear();
                merchantProduct_model.addAll(items);
                merchantProduct_adapter.notifyDataSetChanged();

            }
            else if(jsonObject.get("status").equals("fail")){
                MDToast mdToast = MDToast.makeText(Add_Product.this, jsonObject.getString("message"),3,MDToast.TYPE_ERROR);
                mdToast.setGravity(Gravity.CENTER, 0, 0);
                mdToast.show();

            }

        }
        catch (JSONException e) { e.printStackTrace(); }
    }


}