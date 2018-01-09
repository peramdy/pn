package com.peramdy.redis;

import com.peramdy.builder.FirebaseOkHttpRequestBuilder;
import com.peramdy.constants.CommConstant;
import com.peramdy.model.Notification;
import com.peramdy.utils.SerializationUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

/**
 * @author peramdy
 * @date 2018/1/9
 */
public class SubscribeListener extends JedisPubSub {


    private OkHttpClient client;
    private String authKey;

    public SubscribeListener(OkHttpClient.Builder builder, String authKey) {
        this.client = builder.build();
        this.authKey = authKey;
    }

    public SubscribeListener() {
    }

    /**
     * listener message
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        if (CommConstant.REDIS_CHANNEL_ANDROID_ONE.equalsIgnoreCase(channel)) {
            Notification notification = SerializationUtils.deserialize(message, Notification.class);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(notification.toString());

            if (client == null || authKey == null) {
                return;
            }
            FirebaseOkHttpRequestBuilder builder = new FirebaseOkHttpRequestBuilder(authKey);
            Request request = builder.build(notification);
            try {
                System.out.println("execute start");
                Response response = client.newCall(request).execute();
                System.out.println("execute success");
                System.out.println(response.code());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
        System.out.println(pattern + " : " + channel + " : " + message);
    }
}
