package com.peramdy;

import com.peramdy.net.model.NotificationPayload;
import org.junit.Test;

/**
 * Created by peramdy on 2017/11/9.
 */
public class NotificationPayloadTest {


    @Test
    public void testOne() throws Exception {

        NotificationPayload payload = new NotificationPayload();
        payload.addTitle("hello");


    }


    @Test
    public void testTwo() throws Exception {
        NotificationPayload payload = new NotificationPayload();
        payload.addBadge(1);
        payload.addTitle("hello");
        payload.addBody("test");
        payload.addCustomDictionary("hello", "nihao");
        System.out.println(payload.toString());
    }


    @Test
    public void testThree() {
        int s = Integer.parseInt("10101", 3);
        System.out.println(s);
    }

}
