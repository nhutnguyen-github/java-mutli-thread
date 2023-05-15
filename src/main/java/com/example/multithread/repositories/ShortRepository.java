package com.example.multithread.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class ShortRepository {
  public Integer getDouble(Integer num) {
    return num * 2;
  }
}
