package com.nanlabs.utils.exceptions;

import com.nanlabs.enums.HttpCode;

public class BusinessException extends AbstractApplicationException {
    private static final HttpCode DEFAULT_HTTP_CODE;

    public BusinessException() {
        super();
    }
    public BusinessException(int errorCode,String typeError, String errorMessage, HttpCode httpError) {
        super(errorCode,typeError, errorMessage, httpError);
    }
    public BusinessException(int errorCode,String errorMessage, HttpCode httpError, Object details, Throwable cause, String... messageArgs) {
        super(errorCode, errorMessage, httpError, details, cause, messageArgs);
    }

    protected HttpCode getDefaultHttpCode() {
        return DEFAULT_HTTP_CODE;
    }

    static {
        DEFAULT_HTTP_CODE = HttpCode.UNPROCESSABLE_ENTITY;
    }
}
