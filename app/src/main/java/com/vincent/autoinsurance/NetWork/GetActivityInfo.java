package com.vincent.autoinsurance.NetWork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vincent.autoinsurance.Bean.AutoActivity;
import com.vincent.autoinsurance.Context.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent on 2016/4/13 0013.
 */
public class GetActivityInfo {
    public GetActivityInfo(RequestQueue requestQueue,final String city,final SuccessCallback successCallback, final FailCallback failCallback){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Config.HTTP_SERVER_GET_ACTIVITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Log.i(Config.NET_RESULT_DATA,s);

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            switch (jsonObject.getInt(Config.RESULT_STATUS)){
                                case Config.RESULT_CODE_SUCCESS:
                                    if(successCallback!=null) {
                                        List<AutoActivity> lists = new ArrayList<>();
                                        JSONArray data = jsonObject.getJSONArray(Config.RESULT_DATA);
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject obj = data.getJSONObject(i);
                                            AutoActivity autoActivity = new AutoActivity();
                                            autoActivity.setName(obj.getString(Config.RESULT_ACTIVITY_NAME));
                                            autoActivity.setSummary(obj.getString(Config.RESULT_ACTIVITY_SUMMARY));
                                            autoActivity.setDate(obj.getString(Config.RESULT_ACTIVITY_Date));
                                            autoActivity.setDuration(obj.getString(Config.RESULT_ACTIVITY_DURATION));
                                            autoActivity.setActivityImageUrl(obj.getString(Config.RESULT_ACTIVITY_IMAGE_URL));
                                            autoActivity.setContentUrl(obj.getString(Config.RESULT_ACTIVITY_CONTENT_URL));
                                            lists.add(autoActivity);

                                            Log.i("debug",i+":"+lists.get(i).getName());
                                        }

                                        successCallback.onSuccess(lists);
                                    }
                                    break;
                                case Config.RESULT_STATUS_FAIL:
                                    if(failCallback!=null){
                                        failCallback.onFail(jsonObject.getString(Config.RESULT_INFO));
                                    }
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if(failCallback!=null){
                                failCallback.onFail(Config.ERROR_JSON_EXCPTION);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(failCallback!=null){
                            failCallback.onFail(Config.ERROR);
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put(Config.CITY,city);

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
        void onSuccess(List<AutoActivity> lists);
    }

    public interface FailCallback{
        void onFail(String errorCode);
    }
}
