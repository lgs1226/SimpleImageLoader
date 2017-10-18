package com.lgs.imageloader.utils;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/10/16.
 */

public class ImageViewHelper {

    private final static int defaultWidth = 200;
    private final static int defaultHeight = 200;

    public static int getImageViewWidth(ImageView imageView){
        if (imageView != null){
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int width = 0;
            if (layoutParams != null && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT){
                width = imageView.getWidth();
            }
            if (width <= 0 && layoutParams != null){
                width = layoutParams.width;
            }
            if (width <= 0){
                width = getImageViewFieldValue(imageView , "mMaxWidth");
            }
            return width;
        }
        return defaultWidth;
    }

    public static int getImageViewHeight(ImageView imageView){
        if (imageView != null){
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int height = 0;
            if (layoutParams != null && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT){
                height = imageView.getHeight();
            }
            if (height <= 0 && layoutParams != null){
                height = layoutParams.height;
            }
            if (height <= 0){
                height = getImageViewFieldValue(imageView , "mMaxHeight");
            }
            return height;
        }
        return defaultHeight;
    }

    public static int getImageViewFieldValue(ImageView imageView , String fieldName){
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (int) field.get(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                return fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
