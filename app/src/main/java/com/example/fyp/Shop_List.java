package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.UserAdapter.ShopListAdapter;
import com.example.fyp.UserModel.ShopListModel;
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

public class Shop_List extends AppCompatActivity {

    private List<ShopListModel> shopListModelList;
    private ShopListAdapter shopListAdapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    RecyclerView rv_shop_list;
    SwipeRefreshLayout swipeLayout;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop__list);


        MyToolbar();
        Refresh();
        ShopRecyclerView();
        Get_ShopList();

    }
    private void MyToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shop List");
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
                        Get_ShopList();
                        progressDialog.dismiss();
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShopRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_shop_list = (RecyclerView) findViewById(R.id.rv_shop_list);
        rv_shop_list.setLayoutManager(layoutManager);
        shopListModelList = new ArrayList<>();
        shopListAdapter = new ShopListAdapter(this, shopListModelList);
        rv_shop_list.setAdapter(shopListAdapter);
    }

    private void Get_ShopList(){
        progressDialog = new ProgressDialog(Shop_List.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, BaseUrl.shop_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                SetCategoryResponse(response);
                /// Toast.makeText(User_Dashboard.this, response, Toast.LENGTH_SHORT).show();
            }


        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                // MyData.put("id",merchant_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void SetCategoryResponse(String response) {

        try {

            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("status").equals("success")){
                MDToast mdToast = MDToast.makeText(Shop_List.this,jsonObject.getString("message"),2,MDToast.TYPE_SUCCESS);
                mdToast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                mdToast.show();
                JSONArray jsonarray = new JSONArray(jsonObject.getString("data"));

                Gson gson=new Gson();
                Type listType=new TypeToken<List<ShopListModel>>(){}.getType();
                List<ShopListModel> items=gson.fromJson(jsonarray.toString(),listType);
                shopListModelList.clear();
                shopListModelList.addAll(items);
                shopListAdapter.notifyDataSetChanged();

            }
            else if(jsonObject.get("status").equals("fail")){
                MDToast mdToast = MDToast.makeText(Shop_List.this, jsonObject.getString("message"),3,MDToast.TYPE_ERROR);
                mdToast.setGravity(Gravity.CENTER, 0, 0);
                mdToast.show();

            }

        }
        catch (JSONException e) { e.printStackTrace(); }
    }


}