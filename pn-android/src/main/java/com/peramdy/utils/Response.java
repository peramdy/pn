package com.peramdy.utils;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by peramdy on 2017/7/20.
 */
public class Response {

    private final int httpCode;
    private final String body;
    private final Throwable t;

    public Response(int httpCode, String body, Throwable t) {
        this.httpCode = httpCode;
        this.body = body;
        this.t = t;
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
