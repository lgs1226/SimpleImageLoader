package com.lgs.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/10/11.
 */

public class RequestQueue{

    private final static String TAG = "RequestQueue";

    private int threadCount;
    //阻塞式队列
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>();
    //线程安全
    AtomicInteger ai = new AtomicInteger(0);
    //一组转发器
    private RequestDispatch[] dispatches;

    public RequestQueue(int threadCount){
        this.threadCount = threadCount;
    }

    public void addRequest(BitmapRequest bitmapRequest){
        if (!mRequestQueue.contains(bitmapRequest)){
            bitmapRequest.setSerialNO(ai.incrementAndGet());
            mRequestQueue.add(bitmapRequest);
            Log.i(TAG, "addRequest: "+"添加请求"+bitmapRequest.getSerialNO());
        }else {
            Log.i(TAG, "addRequest: 请求已存在");
        }
    }

    public void stop(){
        if (dispatches != null && dispatches.length > 0){
            for (int i = 0; i < dispatches.length; i++) {
                dispatches[i].interrupt();
            }
        }
    }

    public void start(){
        stop();
        startDispatchers();
    }

    private void startDispatchers() {
        dispatches = new RequestDispatch[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Log.i(TAG, "startDispatchers()");
            RequestDispatch requestDispatch = new RequestDispatch(mRequestQueue);
            dispatches[i] = requestDispatch;
            dispatches[i].start();
        }
    }
}
