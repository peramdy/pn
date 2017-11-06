package com.peramdy.android;

import com.peramdy.android.builder.FirebaseClientBuilder;
import com.peramdy.android.model.Notification;
import com.peramdy.android.okhttpClient.FireBaseClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peramdy on 2017/7/20.
 */
public class MyTest {


    @Test
    public void testOne() {
        List<String> tokens = new ArrayList<String>();
        tokens.add("test1");
        tokens.add("test2");
        Notification notification = new Notification.Builder(tokens)
                .title("消息头部")
                .body("消息内容")
                .sound("======")
                .build();
        System.out.println(notification.getPayload());

    }

    @Test
    public void testTwo() {
        String authKey = "AAAAsxGTNek:APA91bElgIAzuEvnfFFPiDP88pBBc76Ninpp7xG65cR-c52ul9P8Z8bG4LBORAE2EplftMCLH8ljfEq0tbd5jCubBGbJVGNQAiFviA4qxWGre4K25xm6crB5XC0J27u4xr8qajHOncDr";
        FireBaseClient firebaseClient = new FirebaseClientBuilder(authKey).build();
        List<String> tokens = new ArrayList<String>();
        tokens.add("test1");
        tokens.add("test2");
        Notification notification = new Notification.Builder(tokens)
                .title("消息头部")
                .body("消息内容")
                .customFeild("type", 1)
                .sound("======")
                .customFeild("huahua", "nihao")
                .build();
        firebaseClient.push(notification);
    }


}
