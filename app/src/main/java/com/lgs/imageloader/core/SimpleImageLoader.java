package com.lgs.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lgs.imageloader.cache.MemoryCache;
import com.lgs.imageloader.config.DisplayConfig;
import com.lgs.imageloader.config.ImageLoaderConfig;
import com.lgs.imageloader.policy.SerialPolicy;
import com.lgs.imageloader.request.BitmapRequest;
import com.lgs.imageloader.request.RequestQueue;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SimpleImageLoader {

    private ImageLoaderConfig config;
    private RequestQueue requestQueue;
    private static SimpleImageLoader instance;
    private SimpleImageLoader(){

    }

    private SimpleImageLoader(ImageLoaderConfig config){
        this.config = config;
        requestQueue = new RequestQueue(config.getThreadCount());
        requestQueue.start();
    }

    public static SimpleImageLoader getInstance(ImageLoaderConfig config){
        if (instance == null){
            synchronized (SimpleImageLoader.class){
                if (instance == null){
                    instance = new SimpleImageLoader(config);
                }
            }
        }
        return instance;
    }

    public static SimpleImageLoader getObject(){
        if(instance == null){
            throw new UnsupportedOperationException("getInstance(ImageLoaderConfig config) 没有执行过！");
        }
        return instance;
    }

    public void displayImage(String url , ImageView imageView){
        this.displayImage(url , imageView , null , null);
    }

    public void displayImage(String url , ImageView imageView , DisplayConfig config , ImageLoadListener listener){
        BitmapRequest bitmapRequst = new BitmapRequest(imageView , url , config , listener);
        requestQueue.addRequest(bitmapRequst);
    }

    public ImageLoaderConfig getConfig() {
        return config;
    }

    public static interface ImageLoadListener{
        void onComplete(ImageView imageView , Bitmap bitmap , String url);
    }
}
