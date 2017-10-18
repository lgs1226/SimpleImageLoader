package com.lgs.imageloader.request;

import android.util.Log;

import com.lgs.imageloader.loader.Loader;
import com.lgs.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2017/10/11.
 */

public class RequestDispatch extends Thread{

    private BlockingQueue<BitmapRequest> blockingQueue;

    private final static String TAG = "RequestDispatch";

    public RequestDispatch(BlockingQueue<BitmapRequest> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true){
            if (!isInterrupted()){
                try {
                    BitmapRequest take = blockingQueue.take();
                    Log.i(TAG, "run: 处理请求"+take.getSerialNO());
                    String schema = parseSchema(take.getImageUrl());
                    Loader loader = LoaderManager.getInstance().getLoader(schema);
                    loader.loadImage(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String parseSchema(String imageUri) {
        if(imageUri.contains("://")){
            return imageUri.split("://")[0];
        }else{
            Log.e("jason", "图片地址schema异常！");
        }
        return null;
    }
}
