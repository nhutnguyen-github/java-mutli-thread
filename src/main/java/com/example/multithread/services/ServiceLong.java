package com.example.multithread.services;

import java.util.concurrent.ExecutionException;

public interface ServiceLong {
  public int getNextInt(int a) throws InterruptedException, ExecutionException;
}
