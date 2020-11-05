package com.example.fyp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.UserAdapter.Offers_Adapter;
import com.example.fyp.UserAdapter.TopBrandSoptlight_Adapter;
import com.example.fyp.UserModel.ImageModel;
import com.example.fyp.UserModel.Offer_Model;
import com.example.fyp.UserModel.TopBrandSpotlight_Model;
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
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class User_Dashboard extends AppCompatActivity implements LocationListener {


    private List<Offer_Model> offer_modelList;
    private Offers_Adapter offers_adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    RecyclerView recyclerView;
    ProgressDialog progressDialog;



    private List<TopBrandSpotlight_Model> topBrandSpotlight_modelList;
    private TopBrandSoptlight_Adapter topBrandSoptlight_adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    RecyclerView rv_top_brand;


    private TextView tv_currentlocation;
    LocationManager locationManager;


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] Images= {R.drawable.storeone,R.drawable.storetwo,R.drawable.storethree};

    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    ArrayList<ImageModel> imageModels;
    String lat,lag;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard);

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(User_Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(User_Dashboard.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        Init();
        getLocation();
        ImageSlider();
        get_Banner();

        offer_Recycler();
        get_Offer();

        MyBottomview();


    }


    private void Init() {
        tv_currentlocation=(TextView)findViewById(R.id.tv_currentlocation);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,User_Dashboard.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onLocationChanged(Location location) {
        Double lattitude=location.getLatitude();
        Double  longitude=location.getLongitude();

         lat = Double.toString(lattitude);
         lag  = Double.toString(longitude);
         Log.d("lag",lag);
        get_topbrand_spotlight();
        top_brand_Recycler();

      //  Toast.makeText(this, "lattitude"+lat, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(User_Dashboard.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            tv_currentlocation.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void ImageSlider() {
        for(int i=0;i<Images.length;i++)
            ImageArray.add(Images[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ImageAdapter(User_Dashboard.this,ImageArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    private void get_Banner() {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, BaseUrl.banner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
             //   SetBanners(response);
             //     Toast.makeText(User_Dashboard.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(User_Dashboard.this, "error"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //  MyData.put("id",merchant_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

//    private void SetBanners(String response) {
//
//        try {
//
//            imageModels = new ArrayList<>();
//
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("Data");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1=  jsonArray.getJSONObject(i);
//                ImageModel ab = new ImageModel();
//                ab.setImages("BASE_Image"+jsonObject1.getString("img"));
//                imageModels.add(ab);
//            }
//
//            if(imageModels.size()>0) {
//               // mPager.setAdapter(new ImageAdapter(User_Dashboard.this, imageModels));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    private void offer_Recycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.rv_offers);
        recyclerView.setLayoutManager(layoutManager);
        offer_modelList = new ArrayList<>();
        offers_adapter = new Offers_Adapter(this, offer_modelList);
        recyclerView.setAdapter(offers_adapter);
    }

    private void get_Offer() {
        loder();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.latest_offer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                SetCategoryResponse(response);
              //    Toast.makeText(User_Dashboard.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(User_Dashboard.this, "error"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
              //  MyData.put("id",merchant_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void SetCategoryResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
              //  Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonarray = new JSONArray(jsonObject.getString("data"));

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Offer_Model>>() {
                }.getType();
                List<Offer_Model> items = gson.fromJson(jsonarray.toString(), listType);
                offer_modelList.clear();
                offer_modelList.addAll(items);
                offers_adapter.notifyDataSetChanged();

            } else if (jsonObject.get("status").equals("fail")) {
                MDToast mdToast = MDToast.makeText(User_Dashboard.this, jsonObject.getString("message"), 3, MDToast.TYPE_ERROR);
                mdToast.setGravity(Gravity.CENTER, 0, 0);
                mdToast.show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void top_brand_Recycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_top_brand = (RecyclerView) findViewById(R.id.rv_top_brand);
        rv_top_brand.setLayoutManager(layoutManager);
        topBrandSpotlight_modelList = new ArrayList<>();
        topBrandSoptlight_adapter = new TopBrandSoptlight_Adapter(this, topBrandSpotlight_modelList);
        rv_top_brand.setAdapter(topBrandSoptlight_adapter);
    }

    private void get_topbrand_spotlight() {
        loder();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, BaseUrl.top_brand_spotlight, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                set_topbrand_spotlight(response);
               // Toast.makeText(User_Dashboard.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(User_Dashboard.this, "error"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                  MyData.put("latitude",lat);
                  MyData.put("longitude",lag);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    private void set_topbrand_spotlight(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("success")) {
                //  Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonarray = new JSONArray(jsonObject.getString("data"));

                Gson gson = new Gson();
                Type listType = new TypeToken<List<TopBrandSpotlight_Model>>() {
                }.getType();
                List<TopBrandSpotlight_Model> items = gson.fromJson(jsonarray.toString(), listType);
                topBrandSpotlight_modelList.clear();
                topBrandSpotlight_modelList.addAll(items);
                topBrandSoptlight_adapter.notifyDataSetChanged();

            } else if (jsonObject.get("status").equals("fail")) {
                MDToast mdToast = MDToast.makeText(User_Dashboard.this, jsonObject.getString("message"), 3, MDToast.TYPE_ERROR);
                mdToast.setGravity(Gravity.CENTER, 0, 0);
                mdToast.show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private  void loder(){
        progressDialog = new ProgressDialog(User_Dashboard.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void MyBottomview() {

        Bottom_function bottom_function = new Bottom_function();
        bottom_function.initializeSlider(this.findViewById(android.R.id.content), getApplicationContext()); // initialize slider views
        bottom_function.getSlidingMenu(getApplicationContext());

    }



}