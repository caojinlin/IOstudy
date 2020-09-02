package com.cjl.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ReactorNioService {


    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int coreNum = Runtime.getRuntime().availableProcessors();
        ReactorProcessor[] processors = new ReactorProcessor[2 * coreNum];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new ReactorProcessor();
        }

        int index = 0;
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                keys.remove(key);
                if (key.isAcceptable()) {
                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println(String.format("Accept request from {}", socketChannel.getRemoteAddress()));
                    ReactorProcessor processor = processors[(int) ((index++) % coreNum)];
                    processor.addChannel(socketChannel);
                    processor.wakeup();
                }
            }
        }
    }
}
