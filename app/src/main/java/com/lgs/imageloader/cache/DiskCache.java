package com.lgs.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lgs.imageloader.disk.DiskLruCache;
import com.lgs.imageloader.disk.IOUtil;
import com.lgs.imageloader.request.BitmapRequest;
import com.lgs.imageloader.utils.AppProvides;
import com.lgs.imageloader.utils.FileUitls;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/10/11.
 */

public class DiskCache implements BitmapCache{

    private DiskLruCache diskLruCache;

    private final static String TAG = "DiskCache";
    private final static int maxSize = 1024 * 1024 * 10;
    public DiskCache(Context context) {
        initDiskCache(context);
    }

    private void initDiskCache(Context context) {
        try {
            diskLruCache = DiskLruCache.open(FileUitls.getDiskCacheDir(context , "image_cache") ,
                    AppProvides.getAppVersion(context) , 1 , maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(bitmapRequest.getImageUriMD5());
            if (snapshot != null){
                Log.i(TAG, "get: "+bitmapRequest.getImageUrl());
                return BitmapFactory.decodeStream(snapshot.getInputStream(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            editor = diskLruCache.edit(bitmapRequest.getImageUriMD5());
            os = editor.newOutputStream(0);
            if (saveBitmapToDisk(bitmap , os)){
                Log.i(TAG, "put: "+bitmapRequest.getImageUrl()+"success");
                editor.commit();
            }else {
                editor.abort();
            }
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeQuietly(os);
        }
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        try {
            diskLruCache.remove(bitmapRequest.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean saveBitmapToDisk(Bitmap bitmap , OutputStream os){
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , os);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            IOUtil.closeQuietly(bos);
        }
        return true;
    }
}
