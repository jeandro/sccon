package br.com.jeandro.sccon.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
