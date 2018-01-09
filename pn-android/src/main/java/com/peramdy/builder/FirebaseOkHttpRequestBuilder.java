package com.peramdy.builder;

import com.peramdy.constants.CommConstant;
import com.peramdy.model.Notification;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.IOException;

/**
 * @author peramdy
 * @date 2018/1/8.
 */
public class FirebaseOkHttpRequestBuilder {

    private String authKey;
    private final MediaType mediaType = MediaType.parse("application/json");

    public FirebaseOkHttpRequestBuilder(String authKey) {
        this.authKey = authKey;
    }

    public Request build(Notification notification) {
        Request.Builder builder = new Request.Builder();
        builder.header(CommConstant.AUTHORIZATION, authKey);
        builder.header(CommConstant.CONTENT_TYPE, "application/json;charset=UTF-8");
        builder.url(CommConstant.FIREBASE_API_URL);
        builder.post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.writeUtf8(notification.getPayload());
            }
        });
        return builder.build();
    }

}
