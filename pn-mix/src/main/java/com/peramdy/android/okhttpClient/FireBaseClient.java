package com.peramdy.android.okhttpClient;

import com.peramdy.android.comm.Response;
import com.peramdy.android.model.Notification;

/**
 * Created by peramdy on 2017/7/20.
 */
public interface FireBaseClient {

    Response push(Notification notification);

}
