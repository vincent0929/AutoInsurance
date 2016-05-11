package com.vincent.autoinsurance.Context;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Vincent on 2016/4/21 0021.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> bitmapLruCache;

    public BitmapCache(){
        int maxSize=10*1024*1024;
        bitmapLruCache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    @Override
    public Bitmap getBitmap(String s) {
        return bitmapLruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        if(null==bitmapLruCache.get(s)) {
            bitmapLruCache.put(s, bitmap);
        }
    }
}
