package com.peramdy.ios.okhttpClient;

import com.peramdy.ios.comm.Response;
import com.peramdy.ios.model.Notification;

/**
 * Created by peramdy on 2017/7/18.
 */
public interface ApnsClient {

    Response push(Notification notification);

}
