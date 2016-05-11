package com.vincent.autoinsurance.Context;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.Provider;

/**
 * Created by Vincent on 2016/4/2 0002.
 */
public class Config {

    public static final String APP_ID="com.vincent.autoinsurance";

    //配置字符编码
    public static final String CHARSET="utf-8";

    //Handler消息
    public static final int HANDLER_WHAT_ATYCHOOSECITY_CITY=1000;

    //Toast
    public static final String PROMPT="Prompt";

    //调试信息
    public static final String NET_REQUST_URL="net_request_url";
    public static final String NET_REQUST_DATA="net_request_data";
    public static final String NET_RESULT_DATA="net_result_data";
    public static final String DEBUG="debug";

    //网络通信调试
    public static final String RESULT_STATUS="status";
    public static final int RESULT_STATUS_SUCCESS=1;
    public static final int RESULT_STATUS_FAIL=0;
    public static final String RESULT_DATA="result_data";
    public static final String RESULT_INFO="info";

    //Exception
    public static final String ERROR="error";
    public static final String ERROR_JSON_EXCPTION="json exception";

    public static final String GAODE_KEY="key";
    //高德逆地址编码
    public static final String HTTP_SERVER_GAODE_GEOCODE="http://restapi.amap.com/v3/geocode/regeo";

    public static final String KEY="c0e6676d8804735865c3b3b5bbc896b3";
    public static final String LOCATION="location";
    public static final String LOCATION_LATITUDE="city_latitude";
    public static final String LOCATION_LONGITUDE="city_longitude";
    public static final String RESULT_REGEOCODE="regeocode";
    public static final String RESULT_ADDRESS_COMPONENT="addressComponent";
    public static final String RESULT_CITY ="city" ;
    public static final String RESULT_PROVINCE ="province";
    public static final String LOCATED_CITY="Located City";
    //活动信息
    public static final String RESULT_ACTIVITY_NAME="name";

    public static final String RESULT_ACTIVITY_SUMMARY="summary";
    public static final String RESULT_ACTIVITY_Date="publish_date";
    public static final String RESULT_ACTIVITY_DURATION="activity_duration";
    public static final String RESULT_ACTIVITY_IMAGE_URL="image_url";
    public static final String RESULT_ACTIVITY_CONTENT_URL="content_url";
    //以startActivityResult方式的返回值
    public static final int REQUST_CODE_CHOOSE_CITY=2;

    public static final int RESULT_CODE_SUCCESS=1;
    public static final int RESULT_CODE_FAIL=0;
    public static final String SELECTED_CITY="Selected City";

    public static final String CITY="city";

    //获取活动数据地址
    public static final String HTTP_SERVER_GET_ACTIVITY="http://192.168.1.103:8081/AutoInsuranceServer/Home/Activity/getActivityInfo";

    private static String currentCity="沈阳";

    public static String getCurrentCity() {
        return currentCity;
    }

    public static void setCurrentCity(String currentCity) {
        Config.currentCity = currentCity;
    }


    //登录
    public static final String PHONE_NUMBER_MD5="phone_number_md5";
    public static final String TOKEN="token";
    public static final String HTTP_SERVER_CHECK_TOKEN_IS_EXPIRED="http://localhost:8081/AutoInsurance/php/Login/";

    public static void cachePhoneNumberMd5(Context context,String phoneNumberMd5){
        SharedPreferences.Editor editor=context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(Config.PHONE_NUMBER_MD5,phoneNumberMd5);
        editor.commit();
    }

    public static String getCachedPhoneNumberMD5(Context context){
        return context.getSharedPreferences(Config.APP_ID,Context.MODE_PRIVATE).getString(Config.PHONE_NUMBER_MD5,null);
    }

    public static void cachedToken(Context context,String token){
        SharedPreferences.Editor editor=context.getSharedPreferences(Config.APP_ID,Context.MODE_PRIVATE).edit();
        editor.putString(Config.TOKEN,token);
        editor.commit();
    }

    public static String getCachedToken(Context context){
        return context.getSharedPreferences(Config.APP_ID,Context.MODE_PRIVATE).getString(Config.TOKEN,null);
    }
}


