package com.buet.cse18.customExceptions;

public class NoticeNotFoundException extends RuntimeException{
    public NoticeNotFoundException(String message) {
        super(message);
    }
}
