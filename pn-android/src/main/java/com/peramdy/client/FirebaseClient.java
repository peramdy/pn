package com.peramdy.client;

import com.peramdy.model.Notification;
import com.peramdy.response.FirebaseResponse;

/**
 * @author peramdy
 * @date 2018/1/8.
 */
public interface FirebaseClient {

    FirebaseResponse push(Notification notification);

    void pushByRedisQueue(Notification notification);

}
