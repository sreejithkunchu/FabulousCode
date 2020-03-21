package com.fabulous.code.response.exceptions;


public class UnSupportedObjectException extends RuntimeException {
    String message = "Only flat objects are supported for comparison" +
            " please avoid passing nested object";

    public UnSupportedObjectException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
