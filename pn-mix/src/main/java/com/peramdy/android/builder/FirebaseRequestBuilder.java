package com.peramdy.android.builder;

import com.peramdy.android.comm.HttpConstant;
import com.peramdy.android.model.Notification;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.IOException;

/**
 * Created by peramdy on 2017/7/20.
 */
public class FirebaseRequestBuilder {

    private String authKey;

    private final MediaType mediaType = MediaType.parse("application/json");


    public FirebaseRequestBuilder(String authKey) {
        this.authKey = authKey;
    }

    public Request build(Notification notification) {

        Request.Builder rb = new Request.Builder();

        rb.url(HttpConstant.FIREBASE_API_URL);
        rb.post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(notification.getPayload());
            }
        });
        rb.header("Authorization", "key=" + authKey);
        rb.header("Content-Type", "application/json;charset=UTF-8");
        return rb.build();
    }

}
