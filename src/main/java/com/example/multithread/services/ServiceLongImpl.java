package com.example.multithread.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.example.multithread.repositories.LongRepository;

@Service
public class ServiceLongImpl implements ServiceLong {

    @Autowired
    @Qualifier(value = "threadService")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private LongRepository longRepository;

    public int getNextInt(int a) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("service 10 seconds");
                Thread.sleep(10 * 1000);

                if (a == 0)
                    throw new InterruptedException("num = 0");
                return longRepository.getTriple(a);
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, threadPoolTaskExecutor);

        return completableFuture.get();
    }
}
