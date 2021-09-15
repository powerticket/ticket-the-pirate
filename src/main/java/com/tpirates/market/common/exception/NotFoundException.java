package com.tpirates.market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final int code;

    public NotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }
}
