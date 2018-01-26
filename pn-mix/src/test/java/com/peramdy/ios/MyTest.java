package com.peramdy.ios;

import com.peramdy.ios.builder.ApnsClientBuilder;
import com.peramdy.ios.model.Notification;
import com.peramdy.ios.okhttpClient.ApnsClient;
import okhttp3.*;
import okio.BufferedSink;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author peramdy on 2017/7/18.
 */
public class MyTest {


    @Test
    public void testTwo() throws Exception {
//        String token = "765803bd54428cb6849d708afb325ee6cac278736d090763e952d674b24c0d0b";
//        InputStream in = new FileInputStream("D:\\program\\apns\\certification\\prod.p12");
//        String token = "99b2557dd6bb5149eb037daae23f18e95c4111e5aaeb25b77654a716a3c080b8";
        String token = "";
        InputStream in = new FileInputStream("D:\\abc.p12");
        ApnsClientBuilder builder = new ApnsClientBuilder(in, "123456", "com.SenseNow.app");
        builder.isProduction(false);
        ApnsClient apnsClient = builder.build();
        Notification notification = new Notification.Builder(token)
                .alertBody("你好")
                .alertTitle("nihao")
                .badge(2)
                .customField("type", 1111)
                .build();
        apnsClient.push(notification);

    }


    @Test
    public void testThree() throws Exception {
//        String token = "765803bd54428cb6849d708afb325ee6cac278736d090763e952d674b24c0d0b";
        String token = "99b2557dd6bb5149eb037daae23f18e95c4111e5aaeb25b77654a716a3c080b8";
        String apnsAuthKey = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgGesVEidvb2Oxk0afH5OxUM3WMN6bDbIOepMfhX1fGwSgCgYIKoZIzj0DAQehRANCAAQgmivv7uyzHK2LE86tVly4LcDpXQE9EZIbVj8Cs/2k5/OKhyykUn34jIdKzXlL0CPYbe1Wr+Kzk1B4EHph+cjI";
        String teamId = "32VNW8Q34V";
        String keyId = "U4V3A64WL7";
        ApnsClientBuilder builder = new ApnsClientBuilder(apnsAuthKey, teamId, keyId, "com.SenseNow.app");
        builder.isProduction(false);
//        builder.isAsynchronous(true);
        ApnsClient apnsClient = builder.build();
        Notification notification = new Notification.Builder(token)
                .alertBody("测试！")
                .alertTitle("你好")
                .badge(1)
                .customField("type", 1)
                .build();
        apnsClient.push(notification);

    }

    @Test
    public void testOne() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.10.131:8210/msg1/send")
                .post(new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("application/json");
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.writeUtf8("{\"userId\":110,\"title\":\"hello boot!\",\"body\":\"你好吗？\",\"type\":1}");
                    }
                })
                .build();


        try {
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    System.out.println("fail");
                }

                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("success");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
