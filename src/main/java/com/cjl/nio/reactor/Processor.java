package com.cjl.nio.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processor {

    private static final ExecutorService service = Executors.newFixedThreadPool(16);

    public void process(SelectionKey selectionKey) {
        service.submit(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int count = socketChannel.read(buffer);
            if (count < 0) {
                socketChannel.close();
                selectionKey.cancel();
                System.out.println(String.format("{}\t Read ended", socketChannel));
                return null;
            } else if(count == 0) {
                return null;
            }
            System.out.println(String.format("{}\t Read message {}", socketChannel, new String(buffer.array())));
            return null;
        });
    }
}
