package com.example.exceptions;

public class NotFoundCertificateResultException extends RuntimeException{
    public NotFoundCertificateResultException(String message) {super(message);}

    public NotFoundCertificateResultException(){}
}
