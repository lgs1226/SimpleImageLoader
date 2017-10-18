package com.lgs.imageloader.cache;

import android.graphics.Bitmap;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/11.
 */

public class NoCache implements BitmapCache {
    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return null;
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {

    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {

    }
}
