package com.peramdy.exception;

/**
 * @author peramdy
 * @date 2018/1/8
 */
public class CustomException extends RuntimeException {


    private String message;
    private Throwable targetException;

    public CustomException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getTargetException() {
        return targetException;
    }

    public void setTargetException(Throwable targetException) {
        this.targetException = targetException;
    }

    public CustomException(String message) {
        super(message);
        setMessage(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
        setTargetException(cause);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        setTargetException(cause);
    }
}
