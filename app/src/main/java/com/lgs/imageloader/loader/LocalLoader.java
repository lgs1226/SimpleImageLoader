package com.lgs.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.lgs.imageloader.request.BitmapRequest;
import com.lgs.imageloader.utils.BitmapDecode;
import com.lgs.imageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by Administrator on 2017/10/13.
 */

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        final String path = Uri.parse(request.getImageUrl()).getPath();
        File file = new File(path);
        if (!file.exists()){
            return null;
        }
        BitmapDecode bitmapDecode = new BitmapDecode() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path);
            }
        };
        return bitmapDecode.compressBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()) , ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
