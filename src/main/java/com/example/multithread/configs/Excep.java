package com.example.multithread.configs;

import java.io.IOException;

public class Excep {
    public void name() throws IOException {
        System.out.println("check ex");

        Exception ex = new Exception("123");
        throw new RuntimeException(ex.getCause());
    }

    public static void main(String[] args) {
        try {
            Excep excep = new Excep();
            excep.name();
        } catch (IOException e) {
            System.out.println("123456");
        }
    }
}
