package com.example.multithread.services;

import java.util.concurrent.ExecutionException;

public interface ServiceShort {
  public int getNextTwoInt(int a) throws InterruptedException, ExecutionException;
}
