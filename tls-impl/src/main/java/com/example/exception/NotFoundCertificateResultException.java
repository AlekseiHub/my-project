package com.example.exception;

public class NotFoundCertificateResultException extends RuntimeException{

    public NotFoundCertificateResultException(String message) {super(message);}

    public NotFoundCertificateResultException(){}
}
