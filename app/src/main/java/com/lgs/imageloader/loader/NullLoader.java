package com.lgs.imageloader.loader;

import android.graphics.Bitmap;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/13.
 */

public class NullLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
