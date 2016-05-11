package com.vincent.autoinsurance.Context;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Vincent on 2016/4/4 0004.
 */
public class MyApplication extends Application {

    private static RequestQueue mRequestQueue;

    private static BitmapCache bitmapCache;
    private static ImageLoader imageLoader;


    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue= Volley.newRequestQueue(this);
        Log.i("debug:","Volley.newRequestQueue()");

    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static ImageLoader getImageLoader(){
        bitmapCache=new BitmapCache();
        imageLoader=new ImageLoader(MyApplication.getRequestQueue(),bitmapCache);
        return imageLoader;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mRequestQueue=null;
    }
}
