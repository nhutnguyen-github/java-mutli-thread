package com.example.multithread.advices;

import java.util.concurrent.RejectedExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String getExceptionMessage(Exception excep){
        return new StringBuilder("Server need time to process.").append(excep.getMessage()).toString();
    }

    @ExceptionHandler(InterruptedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String getInterruptedException(InterruptedException excep){
        return new StringBuilder("InterruptedException: ").append(excep.getMessage()).toString();
    }

    @ExceptionHandler(RejectedExecutionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String getRejectedExecutionException(RejectedExecutionException excep){
        return new StringBuilder("RejectedExecutionException: ").append(excep.getMessage()).toString();
    }
}
