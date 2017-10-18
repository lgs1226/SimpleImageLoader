package com.lgs.imageloader.policy;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/11.
 */

public interface LoadPolicy {

    public int compareTo(BitmapRequest request1, BitmapRequest request2);

}
