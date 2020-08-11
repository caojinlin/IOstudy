package com.cjl.aio;


public class TimeClient {

    public static void main(String[] args) {
        int port = 8081;
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();

    }
}
