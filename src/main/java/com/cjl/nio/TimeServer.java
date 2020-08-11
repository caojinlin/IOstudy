package com.cjl.nio;

import java.io.IOException;


public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
