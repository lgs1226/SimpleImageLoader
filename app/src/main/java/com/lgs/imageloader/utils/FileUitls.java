package com.lgs.imageloader.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/10/18.
 */

public class FileUitls {

    public static File getDiskCacheDir(Context context , String fileName){
        String cachePath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.pathSeparator + fileName);
    }

}
