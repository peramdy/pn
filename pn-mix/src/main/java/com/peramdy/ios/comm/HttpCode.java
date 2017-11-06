package com.peramdy.ios.comm;

/**
 * Created by peramdy on 2017/7/20.
 */
public enum HttpCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),

    NOT_MODIFIED(304, "Not Modified"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),

    SERVER_ERROR(500, "Internal Server Error"),
    SERVER_UNAVAILABLE(503, "Service Unavailable"),

    EXCEPTION(-1,"EXCEPTION")
    ;




    private final int code;
    private final String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static HttpCode getHttpCode(int code) {
        for (HttpCode httpCodes : HttpCode.values()) {
            if (httpCodes.getCode() == code)
                return httpCodes;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
