package org.lukedowell.carte.server;


import org.lukedowell.carte.server.handler.MessageHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ldowell on 12/16/15.
 */
public class CarteServer {

    /** The port to listen for incoming connections */
    public static final int SERVER_PORT = 8008;

    /** The number of threads to allocate to our executor service */
    public static final int POOL_SIZE = 5;

    /** The maximum number of bytes our channel can receive at once */
    public static final int BUFFER_SIZE = 128;

    /** The listener */
    private DatagramChannel channel;

    /** Thread pool */
    private final ExecutorService pool;

    public CarteServer() throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(SERVER_PORT));

        pool = Executors.newFixedThreadPool(POOL_SIZE);

        listen();
    }

    private void listen() {
        System.out.println("Server listening on port: " + SERVER_PORT);
        try {
            while(!Thread.currentThread().isInterrupted()) {

                // Set up the buffer
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.clear();

                // Receive a message
                SocketAddress sender = channel.receive(buffer);

                // Pass to handler. How to respond?
                pool.execute(new MessageHandler(buffer, sender));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
