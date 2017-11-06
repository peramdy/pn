package com.peramdy.android.builder;

import com.peramdy.android.okhttpClient.FireBaseClient;
import com.peramdy.android.okhttpClient.impl.AsyOkHttpApnsClient;
import com.peramdy.android.okhttpClient.impl.SyncOkHttpApnsClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by peramdy on 2017/7/20.
 */
public class FirebaseClientBuilder {

    private String authKey; //YOUR_SERVER_KEY (Firebase)
    private OkHttpClient.Builder builder;  //builder
    private ConnectionPool connectionPool; //链接数
    private Boolean isAsynchronous = false;  //是否异步

    public FirebaseClientBuilder(String authKey) {
        this.authKey = authKey;
    }

    public OkHttpClient.Builder createDefaultBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(10, 10, TimeUnit.SECONDS));
        return builder;
    }

    public FireBaseClient build() {

        if (authKey == null)
            return null;

        if (builder == null)
            builder = createDefaultBuilder();

        if (connectionPool != null)
            builder.connectionPool(connectionPool);

        if (isAsynchronous) {
            return new AsyOkHttpApnsClient(authKey, builder);
        } else {
            return new SyncOkHttpApnsClient(authKey, builder);
        }
    }

}
