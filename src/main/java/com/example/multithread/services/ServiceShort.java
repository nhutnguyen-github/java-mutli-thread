package com.example.multithread.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceShort {

    public int getNextTwoInt(int a) throws InterruptedException {
        System.out.println("service 2 seconds");
        Thread.sleep(2 * 1000);

        if (a == 0) throw new InterruptedException("num = 0");
        return a * 2;
    }
}
