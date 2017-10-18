package com.lgs.imageloader.loader;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class LoaderManager {

    public static LoaderManager instance = new LoaderManager();
    private final static String TAG = "LoaderManager";
    //加载器管理容器
    private Map<String, Loader> mLoaderMap = new HashMap<String, Loader>();

    private LoaderManager() {
        register("http" , new UrlLoader());
        register("https" , new UrlLoader());
        register("file" , new LocalLoader());
    }

    public static LoaderManager getInstance(){
        return instance;
    }

    public Loader getLoader(String scheme){
        Log.i(TAG, "getLoader: "+scheme);
        if (mLoaderMap.containsKey(scheme)){
            return mLoaderMap.get(scheme);
        }
        return new NullLoader();
    }

    private void register(String key, Loader loader) {
        mLoaderMap.put(key , loader);
    }
}
