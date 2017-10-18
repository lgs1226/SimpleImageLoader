package com.lgs.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.lgs.imageloader.cache.BitmapCache;
import com.lgs.imageloader.config.DisplayConfig;
import com.lgs.imageloader.core.SimpleImageLoader;
import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/11.
 */

public abstract class AbstractLoader implements Loader {

    private static final String TAG = "AbstractLoader";

    BitmapCache bitmapCache = SimpleImageLoader.getObject().getConfig().getBitmapCache();

    DisplayConfig displayConfig = SimpleImageLoader.getObject().getConfig().getDisplayConfig();
    @Override
    public void loadImage(BitmapRequest request) {
        showLoadBeforeImage(request);
        Bitmap bitmap = bitmapCache.get(request);
        if (bitmap == null){
            Log.i(TAG, "loadImage: 缓存为空！");
            bitmap = onLoad(request);
            catcheImage(request , bitmap);
        }
        deliveryToUIThread(request , bitmap);
    }

    private void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView != null){
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request , bitmap);
                }
            });
        }
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (bitmap != null && imageView.getTag().equals(request.getImageUrl())){
            imageView.setImageBitmap(bitmap);
        }
        if (bitmap == null && hasFailLoadImage()){
            Log.i(TAG, "updateImageView: "+imageView.getTag().toString());
            imageView.setImageResource(displayConfig.getFildRes());
        }
    }

    private void catcheImage(BitmapRequest bitmapRequest , Bitmap bitmap) {
        if (bitmapCache != null && bitmap != null){
            synchronized (bitmapCache){
                bitmapCache.put(bitmapRequest , bitmap);
            }
        }
    }

    protected boolean hasBeforeLoadImage(){
        return (displayConfig != null && displayConfig.getLoadingRes() > 0);
    }

    protected boolean hasFailLoadImage(){
        return (displayConfig != null && displayConfig.getFildRes() > 0);
    }

    protected abstract Bitmap onLoad(BitmapRequest request);

    protected void showLoadBeforeImage(final BitmapRequest bitmapRequest){
        if (hasBeforeLoadImage()){
            final ImageView imageView = bitmapRequest.getImageView();
            if (imageView != null){
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.getLoadingRes());
                    }
                });
            }
        }
    }
}
