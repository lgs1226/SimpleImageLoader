package com.lgs.imageloader.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/11.
 */

public class MemoryCache implements BitmapCache{

    private final static String TAG = "MemoryCache";
    private LruCache<String , Bitmap> lruCache;
    private int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
    public MemoryCache() {
        Log.i(TAG, "MemoryCache: "+maxSize);
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                Log.i(TAG, "sizeOf: "+value.getRowBytes() * value.getHeight() / 1024);
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        Log.i(TAG, "get() "+bitmapRequest.getImageUrl());
        return lruCache.get(bitmapRequest.getImageUriMD5());
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        if (get(bitmapRequest) == null){
            Log.i(TAG, "put()"+bitmapRequest.getImageUrl());
            lruCache.put(bitmapRequest.getImageUriMD5() , bitmap);
        }
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        Log.i(TAG, "remove()"+bitmapRequest.getImageUrl());
        lruCache.remove(bitmapRequest.getImageUriMD5());
    }
}
