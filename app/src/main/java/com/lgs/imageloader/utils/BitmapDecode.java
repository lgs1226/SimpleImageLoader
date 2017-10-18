package com.lgs.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2017/10/16.
 */

public abstract class BitmapDecode {

    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

    public Bitmap compressBitmap(int reqWidth , int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //计算缩放比例
        calculateBitmapWithOption(reqWidth , reqHeight , options);
        //读取根据比例缩放后的真实图片
        return decodeBitmapWithOption(options);
    }


    private void calculateBitmapWithOption(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight){
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(heightRatio , widthRatio);
        }
        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
    }
}
