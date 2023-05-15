package com.example.multithread.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.multithread.services.ServiceLong;
import com.example.multithread.services.ServiceShort;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ThreadsController {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final ServiceShort serviceShort;
    
    private final ServiceLong serviceLong;

    @GetMapping("/getshort/{num}")
    @ResponseBody
    public Integer getShort(@PathVariable(value = "num", required = false) int num) throws Exception {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {                
                return serviceShort.getNextTwoInt(num);
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, threadPoolTaskExecutor);

        int rs = 0;
        try {
            rs = completableFuture.get();
        } catch (Exception e) {
            try {
                throw e.getCause();
            } catch (InterruptedException | RejectedExecutionException | ExecutionException ex) {
                System.out.println("ex: " + ex.getClass());
                throw ex;
            } catch (Throwable e1) {
                System.out.println("ex: " + e1.getClass());
                throw new InterruptedException(e1.getMessage());
            } 
        }

        return rs;
    }

    @GetMapping("/getlong/{num}")
    @ResponseBody
    public Integer getLong(@PathVariable(value = "num", required = false) int num) throws InterruptedException, RejectedExecutionException {

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return serviceLong.getNextInt(num);
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, threadPoolTaskExecutor);

        int rs = 0;
        try {
            rs = completableFuture.join();
        } catch (CompletionException e) {
            try {
                throw e.getCause();
            } catch (InterruptedException | RejectedExecutionException ex) {
                System.out.println("ex: " + ex.getClass());
                throw ex;
            } catch (Throwable e1) {
                System.out.println("ex: " + e1.getClass());
                throw new InterruptedException(e1.getMessage());
            } 
        }

        return rs;
    }

}
