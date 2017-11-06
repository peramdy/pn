package com.peramdy.ios.comm;

/**
 * Created by peramdy on 2017/7/20.
 */
public class Response {

    private final int httpCode;
    private final String body;
    private final Throwable t;
    private final HttpCode status;

    public Response(int httpCode, String body, Throwable t, HttpCode status) {
        this.httpCode = httpCode;
        this.body = body;
        this.t = t;
        this.status = status;
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

    public HttpCode getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "httpCode=" + httpCode +
                ", body='" + body + '\'' +
                ", t=" + t +
                ", status=" + status +
                '}';
    }
}
