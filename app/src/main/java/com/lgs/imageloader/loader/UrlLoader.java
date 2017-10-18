package com.lgs.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lgs.imageloader.request.BitmapRequest;
import com.lgs.imageloader.utils.BitmapDecode;
import com.lgs.imageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/10/13.
 */

public class UrlLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(request.getImageUrl()).openConnection();
            urlConnection.setConnectTimeout(4000);
            urlConnection.setReadTimeout(4000);
            final Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            BitmapDecode bitmapDecode = new BitmapDecode() {
                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    return bitmap;
                }
            };
            return bitmapDecode.compressBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()), ImageViewHelper.getImageViewHeight(request.getImageView()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
