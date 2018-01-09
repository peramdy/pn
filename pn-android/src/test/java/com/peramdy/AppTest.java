package com.peramdy;

import com.peramdy.builder.FirebaseClientFactory;
import com.peramdy.client.FirebaseClient;
import com.peramdy.constants.CommConstant;
import com.peramdy.model.Notification;
import com.peramdy.redis.RedisConfig;
import com.peramdy.redis.SubscribeListener;
import com.peramdy.redis.SubscribeTask;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AppTest extends TestCase {

    public void testOne() {
        List<String> tokens = new ArrayList<>();
        tokens.add("sdsd");
        try {
            FirebaseClient client = new FirebaseClientFactory.Builder("12313")
                    .connectTime(5).keepAliveDuration(5).build();

            Notification notification = new Notification.Builder(tokens)
                    .title("你好")
                    .body("hello")
                    .condition("condition")
                    .customFeild("ha", "ha")
                    .build();
            System.out.println(notification.toString());
            System.out.println(notification.getPayload());
            client.push(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testTwo() {
        List<String> tokens = new ArrayList<>();
        tokens.add("123456789");
        try {

            SubscribeListener listener = new SubscribeListener();
            String[] channels = {CommConstant.REDIS_CHANNEL_ANDROID_ONE};
            RedisConfig config = new RedisConfig("192.168.136.130", 16379);
            SubscribeTask task = new SubscribeTask(config, listener, channels);

            Thread t = new Thread(task);
            t.start();

            FirebaseClient client = new FirebaseClientFactory.Builder("12313")
                    .redisUrl("192.168.136.130")
                    .redisPort(16379)
                    .build();
            Notification notification = new Notification.Builder(tokens)
                    .title("你好")
                    .body("hello")
                    .condition("condition")
                    .customFeild("ha", "ha")
                    .build();
            System.out.println(notification.getPayload());
//            for (int i = 0; i < 100; i++)
            client.pushByRedisQueue(notification);
//            listener.unsubscribe();
            System.out.println("send success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testThree() {
        List<String> tokens = new ArrayList<>();
        tokens.add("123456789");
        try {
            FirebaseClient client = new FirebaseClientFactory.Builder("12313")
                    .redisUrl("192.168.136.130")
                    .redisPort(16379)
                    .buildQueue();
            Notification notification = new Notification.Builder(tokens)
                    .title("你好")
                    .body("hello")
                    .condition("condition")
                    .customFeild("ha", "ha")
                    .build();
            System.out.println(notification.getPayload());
//            for (int i = 0; i < 100; i++)
            client.pushByRedisQueue(notification);
            System.out.println("send success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
