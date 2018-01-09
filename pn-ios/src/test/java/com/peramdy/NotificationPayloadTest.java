package com.peramdy;

import com.peramdy.net.PushNotificationManager;
import com.peramdy.net.model.NotificationPayload;
import com.peramdy.net.server.impl.AppleNotificationServerBasicImpl;
import junit.framework.TestCase;

/**
 * Created by peramdy on 2017/11/9.
 */
public class NotificationPayloadTest extends TestCase {


    public void testOne() throws Exception {

        NotificationPayload payload = new NotificationPayload();
        payload.addTitle("hello");


    }


    public void testTwo() throws Exception {
        NotificationPayload payload = new NotificationPayload();
        payload.addBadge(1);
        payload.addTitle("hello");
        payload.addBody("test");
        payload.addCustomDictionary("hello", "nihao");
        System.out.println(payload.toString());
    }


    public void testThree() {
        int s = Integer.parseInt("10101", 3);
        System.out.println(s);
    }

    public void testFour() {
        try {
            PushNotificationManager pushNotificationManager = new PushNotificationManager();
            pushNotificationManager.initializeConnection(new AppleNotificationServerBasicImpl("", "", true));
//            pushNotificationManager.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
