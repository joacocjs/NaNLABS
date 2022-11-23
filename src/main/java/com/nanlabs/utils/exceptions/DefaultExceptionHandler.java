package com.nanlabs.utils.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException exception) {
        return this.buildResponse(exception);
    }

    protected ResponseEntity<ApiErrorResponse> buildResponse(BusinessException exception) {
        return ApiErrorResponse.Entity.of(exception.getHttpStatus(), exception.getIntErrorCode(), "Business Exception",
                buildMessages(exception.getMessageArgs()), exception.getDetails());
    }

    protected String buildMessages(String[] messages){
        return  (messages != null)? String.join("; ", messages) : "";
    }
}
