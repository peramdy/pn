package com.peramdy.ios.okhttpClient.impl;

import com.peramdy.ios.builder.ApnsRequestBuilder;
import com.peramdy.ios.model.Notification;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by peramdy on 2017/7/19.
 * 异步
 */
public class AsyOkHttpApnsClient extends SyncOkHttpApnsClient {


    public AsyOkHttpApnsClient(InputStream certificate, String password, String defaultTopic, Boolean isProduction, OkHttpClient.Builder builder) throws Exception {
        super(certificate, password, defaultTopic, isProduction, builder);
    }

    public AsyOkHttpApnsClient(String apnsAuthKey, String teamId, String keyId, String defaultTopic, Boolean isProduction, OkHttpClient.Builder builder) {
        super(apnsAuthKey, teamId, keyId, defaultTopic, isProduction, builder);
    }

    @Override
    @Deprecated
    public com.peramdy.ios.comm.Response push(Notification notification) {
        ApnsRequestBuilder apnsRequestBuilder = new ApnsRequestBuilder(
                super.getApnsAuthKey(),
                super.getTeamId(),
                super.getKeyId(),
                super.getGateway(),
                super.getDefaultTopic()
        );


        Request request = apnsRequestBuilder.build(notification);
        super.client.newCall(request).enqueue(new Callback() {

            public void onFailure(Call call, IOException e) {
//                new com.peramdy.ios.comm.Response(-1, null, e, HttpCode.getHttpCode(-1));
                System.out.println("---------send fail----------");
            }

            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("---------send success----------");
            }
        });

        return null;
    }

    @Override
    public OkHttpClient getClient() {
        return super.getClient();
    }
}
