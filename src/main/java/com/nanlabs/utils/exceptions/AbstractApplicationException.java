package com.nanlabs.utils.exceptions;

import com.nanlabs.enums.HttpCode;
import org.springframework.http.HttpStatus;

public abstract class AbstractApplicationException extends RuntimeException {
    private final int errorCode;
    private final HttpCode httpError;
    private final Object details;
    private final String[] messageArgs;

    protected abstract HttpCode getDefaultHttpCode();

    public AbstractApplicationException() {
        this.httpError = this.getDefaultHttpCode();
        this.details = "";
        this.errorCode = 0;
        this.messageArgs = null;
    }

    AbstractApplicationException(int errorCode, String typeError, String errorMessage,HttpCode httpError) {
        super(errorMessage);
        this.httpError = httpError;
        this.errorCode = errorCode;
        this.details = typeError;
        this.messageArgs = new String[]{errorMessage};
    }

    AbstractApplicationException(int errorCode, String errorMessage,HttpCode httpError, Object details, Throwable cause, String... messageArgs) {
        super(errorMessage, cause);
        this.httpError = httpError;
        this.errorCode = errorCode;
        this.details = details;
        this.messageArgs = messageArgs;
    }

    public int getIntErrorCode() {
        return this.errorCode;
    }

    public HttpStatus getHttpStatus() {
        return this.httpError.unwrapType();
    }

    public Object getDetails() {
        return this.details;
    }

    public String[] getMessageArgs() {
        return this.messageArgs;
    }
}
