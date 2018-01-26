package com.peramdy.net.exception;

/**
 * @author peramdy on 2018/1/11.
 */
public class CustomException extends RuntimeException {

    private String message;
    private Throwable throwable;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public CustomException(String message) {
        super(message);
        setMessage(message);
    }

    public CustomException(Throwable throwable) {
        super(throwable);
        setThrowable(throwable);
    }

    public CustomException(String message, Throwable throwable) {
        super(message, throwable);
        setThrowable(throwable);
        setMessage(message);
    }

}
