package com.vincent.autoinsurance.NetWork;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vincent.autoinsurance.Context.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 2016/3/15 0015.
 */
public class CheckTokenIsExpired {
    public CheckTokenIsExpired(final String phone_number_md5,final SuccessCallback successCallback,final FailCallback failCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.HTTP_SERVER_CHECK_TOKEN_IS_EXPIRED,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object=new JSONObject(s);
                            switch(object.getInt(Config.RESULT_STATUS)){
                                case Config.RESULT_STATUS_SUCCESS:
                                    if(successCallback!=null){
                                        successCallback.onSuccess(object.getString(Config.TOKEN));
                                    }
                                    break;
                                default:
                                    if(failCallback!=null){
                                        failCallback.onFail(Config.ERROR);
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
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(failCallback!=null){
                    failCallback.onFail(volleyError.getMessage());
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("charset","UTF-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Config.PHONE_NUMBER_MD5,phone_number_md5);

                Log.i(Config.PHONE_NUMBER_MD5,phone_number_md5);

                return params;
            }
        };
    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail(String errorCode);
    }
}
