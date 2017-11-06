package com.peramdy.android.okhttpClient.impl;

import com.peramdy.android.builder.FirebaseRequestBuilder;
import com.peramdy.android.comm.Response;
import com.peramdy.android.model.Notification;
import com.peramdy.android.okhttpClient.FireBaseClient;
import com.peramdy.ios.comm.HttpCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * Created by peramdy on 2017/7/20.
 * 同步
 */
public class SyncOkHttpApnsClient implements FireBaseClient {


    protected final OkHttpClient client;

    private final String authKey;

    public SyncOkHttpApnsClient(String authKey, OkHttpClient.Builder builder) {
        this.client = builder.build();
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    @Override
    public Response push(Notification notification) {
        Response response = null;
        try {
            FirebaseRequestBuilder requestBuilder = new FirebaseRequestBuilder(authKey);
            Request request = requestBuilder.build(notification);
            okhttp3.Response rp = client.newCall(request).execute();
            response = parseResponse(rp);
            System.out.println(response.getHttpCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    protected Response parseResponse(okhttp3.Response response) {
        String contentBody = null;
        int statusCode = 0;
        HttpCode status = null;
        try {
            statusCode = response.code();
            status = HttpCode.getHttpCode(statusCode);
            if (statusCode != 200)
                contentBody = response.body() != null ? response.body().toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(-1, null, e, HttpCode.getHttpCode(-1));
        }
        return new Response(statusCode, contentBody, null, status);
    }
}
