package com.lgs.imageloader.policy;

import com.lgs.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/10/17.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNO() - request1.getSerialNO();
    }
}
