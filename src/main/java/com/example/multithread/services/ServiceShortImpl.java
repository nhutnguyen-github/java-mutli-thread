package com.example.multithread.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.example.multithread.repositories.ShortRepository;

@Service
public class ServiceShortImpl implements ServiceShort {

    @Autowired
    @Qualifier(value = "threadService")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ShortRepository repository;

    public int getNextTwoInt(int a) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> futureShort = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("service 2 seconds");

                Thread.sleep(2 * 1000);

                if (a == 0)
                    throw new InterruptedException("num = 0");
                return repository.getDouble(a);
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, threadPoolTaskExecutor);

        return futureShort.get();
    }
}
