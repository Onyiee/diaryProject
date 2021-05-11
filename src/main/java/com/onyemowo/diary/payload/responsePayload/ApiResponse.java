package com.onyemowo.diary.payload.responsePayload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse {
    private LocalDateTime timeStamp;
    private boolean isSuccessful;
    private String message;

    public ApiResponse(boolean isSuccessful, String message){
        this.isSuccessful = isSuccessful;
        this.message = message;
        timeStamp = LocalDateTime.now();
    }
}
