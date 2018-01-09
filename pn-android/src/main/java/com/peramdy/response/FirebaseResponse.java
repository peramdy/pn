package com.peramdy.response;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author peramdy
 * @date 2017/7/20.
 */
public class FirebaseResponse {

    private int httpCode;
    private String body;
    private Throwable t;

    public FirebaseResponse(int httpCode, String body, Throwable t) {
        this.httpCode = httpCode;
        this.body = body;
        this.t = t;
    }


    public FirebaseResponse(int httpCode, String body) {
        this.httpCode = httpCode;
        this.body = body;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getBody() {
        return body;
    }

    public Throwable getT() {
        return t;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
