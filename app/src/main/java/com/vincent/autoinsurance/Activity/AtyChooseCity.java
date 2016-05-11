package com.vincent.autoinsurance.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vincent.autoinsurance.Context.Config;
import com.vincent.autoinsurance.Context.MyApplication;
import com.vincent.autoinsurance.Context.MyException;
import com.vincent.autoinsurance.NetWork.LocateCity;
import com.vincent.autoinsurance.R;
import com.vincent.autoinsurance.widget.RefreshButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AtyChooseCity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView lvAllCity;
    private ListAdapter adapter;
    private List<String> listCity;
    private TextView tvLocatedCity;
    private RefreshButton rbRefresh;

    private LocationManager mLocationManager = null;

    private Location mlocation;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_choose_city);

        init();
        locatingCity();
    }

    public void init() {
        lvAllCity = (ListView) findViewById(R.id.lvAllCityName);
        listCity = new ArrayList<>();
        listCity.add("北京");
        listCity.add("上海");
        listCity.add("深圳");
        listCity.add("沈阳");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listCity);
        lvAllCity.setAdapter(adapter);
        lvAllCity.setOnItemClickListener(this);

        rbRefresh= (RefreshButton) findViewById(R.id.rb_atychoosecity_refresh);
        rbRefresh.setOnClickListener(this);

        tvLocatedCity = (TextView) findViewById(R.id.tvLocatedCity);
        findViewById(R.id.btnSetCity).setOnClickListener(this);
        findViewById(R.id.ibtn_back_to_last_aty).setOnClickListener(this);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!checkLocationPermission()){
            Toast.makeText(AtyChooseCity.this,Config.PROMPT,Toast.LENGTH_SHORT).show();
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mlocation=location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("network_provider:", provider + "开启");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(Config.ERROR, provider + "无法开启");
        }
    };

    private void locatingCity() {

        if(!checkLocationPermission()){
            Toast.makeText(AtyChooseCity.this,Config.PROMPT,Toast.LENGTH_SHORT).show();
            return;
        }
        mlocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        tvLocatedCity.setText("");

        Log.i(Config.LOCATION_LATITUDE + ":", mlocation.getLatitude() + "");
        Log.i(Config.LOCATION_LONGITUDE + ":", mlocation.getLongitude() + "");

        new LocateCity(mlocation.getLatitude()+"",mlocation.getLongitude()+"",
                MyApplication.getRequestQueue(),new LocateCity.SuccessCallback(){
            @Override
            public void onSuccess(String city) {
                tvLocatedCity.setText(city);
                Log.i(Config.LOCATED_CITY,city);
                completeSearch();
            }
        },new LocateCity.FailCallback(){
            @Override
            public void onFail(String errorCode) {
                Log.i(Config.ERROR,errorCode);
                completeSearch();
            }
        });
    }

    public void completeSearch(){
//        if(rbRefresh.isRotate()){
//            rbRefresh.stopRotate();
//        }
        rbRefresh.stopRotate();
    }

    public boolean checkLocationPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!checkLocationPermission()){
            return;
        }
        mLocationManager.removeUpdates(mLocationListener);
        mLocationManager=null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvCity= (TextView) view;
        String chosenCity=tvCity.getText().toString();
        Intent intent=new Intent();
        intent.putExtra(Config.SELECTED_CITY,chosenCity);
        setResult(Config.RESULT_CODE_SUCCESS,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSetCity:
                Intent intent=new Intent();
                intent.putExtra(Config.SELECTED_CITY,tvLocatedCity.getText().toString());
                setResult(Config.RESULT_CODE_SUCCESS,intent);
                finish();
                break;
            case R.id.ibtn_back_to_last_aty:
                setResult(Config.RESULT_CODE_FAIL);
                finish();;
                break;
            case R.id.rb_atychoosecity_refresh:
                if(!rbRefresh.isRotate()) {
                    rbRefresh.startRotate();
                    locatingCity();
                }
                break;
        }
    }
}
