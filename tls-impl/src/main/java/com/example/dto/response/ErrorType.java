package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {
    NOT_FOUND("NOT_FOUND",
            404,
            "ресурс не найден"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR",
            500,
            "На сервере произошла непредвиденная ошибка.Мы уже знаем об этой ошибке и работаем над её устранением."+
            " Пожалуйста, повторите ваш запрос позже."),
    BAD_REQUEST("BAD_REQUEST",400,"Сервер не смог обработать ваш запрос,так как он содержит некорректные или отсутствующие данные. " +
            "Пожалуйста, проверьте введённую информацию и повторите попытку."),
    ALREADY_EXIST("CONFLICT",409,"Запись с таким url существует.");

    private final String status;
    private final int code;
    private final String detail;
}