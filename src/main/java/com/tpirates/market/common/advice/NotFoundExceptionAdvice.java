package com.tpirates.market.common.advice;

import com.tpirates.market.common.ErrorInfo;
import com.tpirates.market.common.dto.ResponseDto;
import com.tpirates.market.common.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseDto<ErrorInfo> notFoundException(NotFoundException e) {
        return ResponseDto.<ErrorInfo>builder()
                .status(e.getCode())
                .message(e.getMessage())
                .build();
    }
}
