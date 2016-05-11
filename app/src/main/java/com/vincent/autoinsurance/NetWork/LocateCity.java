package com.vincent.autoinsurance.NetWork;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vincent.autoinsurance.Context.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 2016/4/3 0003.
 */
public class LocateCity {
    public LocateCity(final String latitude, final String longitude, RequestQueue requestQueue,
                      final SuccessCallback successCallback,final FailCallback failCallback){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Config.HTTP_SERVER_GAODE_GEOCODE,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {

                        Log.i(Config.RESULT_DATA,s);

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            switch (jsonObject.getInt(Config.RESULT_STATUS)){
                                case Config.RESULT_CODE_SUCCESS:
                                    String city = jsonObject.getJSONObject(Config.RESULT_REGEOCODE).
                                            getJSONObject(Config.RESULT_ADDRESS_COMPONENT).
                                            getString(Config.RESULT_CITY);
                                    if (successCallback != null) {
                                        if("[".equals(city.substring(0,1))){
                                            city=jsonObject.getJSONObject(Config.RESULT_REGEOCODE).
                                                    getJSONObject(Config.RESULT_ADDRESS_COMPONENT).
                                                    getString(Config.RESULT_PROVINCE);
                                        }
                                        city = city.substring(0, city.length() - 1);
                                        successCallback.onSuccess(city);
                                    }
                                    break;
                                default:
                                    if (failCallback != null) {
                                        failCallback.onFail(jsonObject.getString(Config.RESULT_INFO));
                                    }
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (failCallback != null) {
                                failCallback.onFail(Config.ERROR_JSON_EXCPTION);
                            }
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(failCallback!=null){
                    failCallback.onFail(volleyError.getMessage());
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put(Config.GAODE_KEY,Config.KEY);
                map.put(Config.LOCATION,longitude+","+latitude);

                Log.i(Config.NET_REQUST_URL,Config.HTTP_SERVER_GAODE_GEOCODE);
                Log.i(Config.NET_REQUST_DATA,map.toString());

                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("Charset", "UTF-8");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    public interface SuccessCallback{
        void onSuccess(String city);
    }

    public interface FailCallback{
        void onFail(String errorCode);
    }
}
