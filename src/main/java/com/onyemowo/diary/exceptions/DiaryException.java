package com.onyemowo.diary.exceptions;

public class DiaryException extends Exception{
    public DiaryException() {
    }

    public DiaryException(String message) {
        super(message);
    }

    public DiaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiaryException(Throwable cause) {
        super(cause);
    }

    public DiaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
