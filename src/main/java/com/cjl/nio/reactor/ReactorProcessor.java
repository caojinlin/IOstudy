package com.cjl.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

public class ReactorProcessor {

    private static final ExecutorService service =
            Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    private Selector selector;

    public ReactorProcessor() throws IOException {
        this.selector = SelectorProvider.provider().openSelector();
        start();
    }

    public void addChannel(SocketChannel socketChannel) throws ClosedChannelException {
        socketChannel.register(this.selector, SelectionKey.OP_READ);
    }

    public void wakeup() {
        this.selector.wakeup();
    }

    public void start() {
        service.submit(() -> {
            while (true) {
                if (selector.select(500) <= 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int count = socketChannel.read(buffer);
                        if (count < 0) {
                            socketChannel.close();
                            key.cancel();
                            System.out.println(format("{}\t Read ended", socketChannel));
                            continue;
                        } else if (count == 0) {
                            System.out.println(format("{}\t Message size is 0", socketChannel));
                            continue;
                        } else {
                            System.out.println(format("{}\t Read message {}", socketChannel, new String(buffer.array())));
                        }
                    }
                }
            }
        });
    }
}
