package com.peramdy.android.okhttpClient.impl;

import com.peramdy.android.builder.FirebaseRequestBuilder;
import com.peramdy.android.comm.Response;
import com.peramdy.android.model.Notification;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * Created by peramdy on 2017/7/20.
 * 异步
 */
public class AsyOkHttpApnsClient extends SyncOkHttpApnsClient {

    public AsyOkHttpApnsClient(String authKey, OkHttpClient.Builder builder) {
        super(authKey, builder);
    }

    @Override
    @Deprecated
    public Response push(Notification notification) {

        FirebaseRequestBuilder builder=new FirebaseRequestBuilder(super.getAuthKey());
        Request request=builder.build(notification);
        super.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("----------asySendFail-----------");
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                System.out.println("----------asySendSuccess_code:"+response.code()+"-----------");
            }
        });
        return null;
    }


}
