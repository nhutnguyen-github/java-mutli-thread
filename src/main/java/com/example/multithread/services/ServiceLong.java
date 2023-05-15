package com.example.multithread.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceLong {

    public int getNextInt(int a) throws InterruptedException   {
        System.out.println("service 10 seconds");
        Thread.sleep(10 * 1000);

        if (a == 0) throw new InterruptedException("num = 0");
        return a * 3;
    }
}
