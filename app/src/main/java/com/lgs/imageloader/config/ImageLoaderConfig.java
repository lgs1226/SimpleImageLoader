package com.lgs.imageloader.config;

import com.lgs.imageloader.cache.BitmapCache;
import com.lgs.imageloader.cache.MemoryCache;
import com.lgs.imageloader.cache.NoCache;
import com.lgs.imageloader.policy.LoadPolicy;
import com.lgs.imageloader.policy.SerialPolicy;

/**
 * 配置
 * Created by Administrator on 2017/10/11.
 */

public class ImageLoaderConfig {

    private BitmapCache bitmapCache = new MemoryCache();

    private LoadPolicy loadPolicy = new SerialPolicy();

    private int threadCount = Runtime.getRuntime().availableProcessors();

    private DisplayConfig displayConfig = new DisplayConfig();
    private ImageLoaderConfig(){

    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public static class Builder{

        private ImageLoaderConfig imageLoaderConfig;

        public Builder(){
            imageLoaderConfig = new ImageLoaderConfig();
        }

        public Builder setCachePolicy(BitmapCache bitmapCache){
            imageLoaderConfig.bitmapCache = bitmapCache;
            return this;
        }

        public Builder setPolicyLoad(LoadPolicy load){
            imageLoaderConfig.loadPolicy = load;
            return this;
        }

        public Builder setThreadCount(int count){
            imageLoaderConfig.threadCount = count;
            return this;
        }

        public Builder setLoadingImg(int res){
            imageLoaderConfig.displayConfig.setLoadingRes(res);
            return this;
        }

        public Builder setFailLoadImg(int res){
            imageLoaderConfig.displayConfig.setFildRes(res);
            return this;
        }

        public ImageLoaderConfig build(){
            return imageLoaderConfig;
        }
    }
}
