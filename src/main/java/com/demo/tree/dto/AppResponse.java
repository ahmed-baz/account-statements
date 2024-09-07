package com.demo.tree.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;


public record AppResponse<T>(
        T payload,
        boolean success,
        Integer statusCode,
        String message,
        Date serviceTime
) {

    public static <T> AppResponse<T> ok(T payload) {
        return new AppResponse<>(payload, true, HttpStatus.OK.value(), null, new Date());
    }

    public static <T> AppResponse<T> created(T payload) {
        return new AppResponse<>(payload, true, HttpStatus.CREATED.value(), null, new Date());
    }

    public static <T> AppResponse<T> failed(Integer statusCode, String message) {
        return new AppResponse<>(null, false, statusCode, message, new Date());
    }

}
