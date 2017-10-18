package com.lgs.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/18.
 */

public class DoubleCache implements BitmapCache{

    private MemoryCache memoryCache = new MemoryCache();

    private DiskCache diskCache;

    public DoubleCache(Context context) {
        this.diskCache = new DiskCache(context);
    }


    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        Bitmap bitmap = memoryCache.get(bitmapRequest);
        if (bitmap == null){
            bitmap = diskCache.get(bitmapRequest);
            if (bitmap != null){
                memoryCache.put(bitmapRequest , bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        memoryCache.put(bitmapRequest , bitmap);
        diskCache.put(bitmapRequest , bitmap);
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        memoryCache.remove(bitmapRequest);
        diskCache.remove(bitmapRequest);
    }
}
