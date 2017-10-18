package com.lgs.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.lgs.imageloader.config.DisplayConfig;
import com.lgs.imageloader.core.SimpleImageLoader;
import com.lgs.imageloader.policy.LoadPolicy;
import com.lgs.imageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2017/10/11.
 */

public class BitmapRequest implements Comparable<BitmapRequest>{

    private DisplayConfig displayConfig;
    private String ImageUrl;
    private SoftReference<ImageView> imageSoft;
    private LoadPolicy loadPolicy = SimpleImageLoader.getObject().getConfig().getLoadPolicy();
    private SimpleImageLoader.ImageLoadListener listener;
    private int serialNO;
    private String imageUriMD5;
    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config , SimpleImageLoader.ImageLoadListener listener){
        imageView.setTag(uri);
        this.imageSoft = new SoftReference<ImageView>(imageView);
        this.ImageUrl = uri;
        this.listener = listener;
        imageUriMD5 = MD5Utils.toMD5(uri);
        if(config != null){
            this.displayConfig = config;
        }
    }

    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loadPolicy.compareTo(this , o);
    }

    public void setSerialNO(int serialNO){
        this.serialNO = serialNO;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public ImageView getImageView() {
        return imageSoft.get();
    }

    public int getSerialNO() {
        return serialNO;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loadPolicy == null) ? 0 : loadPolicy.hashCode());
        result = prime * result + serialNO;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (loadPolicy == null) {
            if (other.loadPolicy != null)
                return false;
        } else if (!loadPolicy.equals(other.loadPolicy))
            return false;
        if (serialNO != other.serialNO)
            return false;
        return true;
    }
}
