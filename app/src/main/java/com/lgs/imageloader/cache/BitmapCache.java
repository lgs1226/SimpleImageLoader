package com.lgs.imageloader.cache;

import android.graphics.Bitmap;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/11.
 */

public interface BitmapCache {

    Bitmap get(BitmapRequest bitmapRequest);

    void put(BitmapRequest bitmapRequest , Bitmap bitmap);

    void remove(BitmapRequest bitmapRequest);
}
