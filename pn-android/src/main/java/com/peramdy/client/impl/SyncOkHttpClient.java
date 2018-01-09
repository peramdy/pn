package com.peramdy.client.impl;

import com.peramdy.builder.FirebaseOkHttpRequestBuilder;
import com.peramdy.client.FirebaseClient;
import com.peramdy.constants.CommConstant;
import com.peramdy.model.Notification;
import com.peramdy.redis.PublishMessage;
import com.peramdy.response.FirebaseResponse;
import com.peramdy.utils.SerializationUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author peramdy
 * @date 2018/1/8.
 */
public class SyncOkHttpClient implements FirebaseClient {


    private OkHttpClient client;
    private String authKey;

    private Jedis jedis;


    public SyncOkHttpClient(OkHttpClient.Builder builder, String authKey) {
        this.client = builder.build();
        this.authKey = authKey;
    }

    public SyncOkHttpClient(OkHttpClient.Builder builder, String authKey, Jedis jedis) {
        this.client = builder.build();
        this.authKey = authKey;
        this.jedis = jedis;
    }


    /**
     * getter
     *
     * @return
     */
    public String getAuthKey() {
        return authKey;
    }

    @Override
    public FirebaseResponse push(Notification notification) {

        FirebaseOkHttpRequestBuilder builder = new FirebaseOkHttpRequestBuilder(authKey);
        Request request = builder.build(notification);
        FirebaseResponse firebaseResponse = null;

        try {
            Response response = client.newCall(request).execute();
            firebaseResponse = parseResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firebaseResponse;
    }

    @Override
    public void pushByRedisQueue(Notification notification) {
        String message = SerializationUtils.serializer(notification);
        PublishMessage.publish(jedis, CommConstant.REDIS_CHANNEL_ANDROID_ONE, message);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse okHttpResponse
     *
     * @param response
     * @return
     */
    protected FirebaseResponse parseResponse(Response response) {
        int statusCode = response.code();
        FirebaseResponse firebaseResponse = new FirebaseResponse(statusCode, response.body().toString());
        return firebaseResponse;
    }
}
