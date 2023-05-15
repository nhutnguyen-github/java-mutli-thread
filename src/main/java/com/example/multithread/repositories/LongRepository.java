package com.example.multithread.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class LongRepository {
  public Integer getTriple(Integer num) {
    return num * 3;
  }
}
