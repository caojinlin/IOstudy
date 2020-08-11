package com.cjl.aio;

import java.io.IOException;

public class TimeServer {


    public static void main(String[] args) throws IOException {
        int port = 8081;
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
