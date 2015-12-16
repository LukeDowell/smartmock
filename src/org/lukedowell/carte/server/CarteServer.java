package org.lukedowell.carte.server;


import org.lukedowell.carte.server.handler.MessageHandler;
import org.lukedowell.carte.shared.Network;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ldowell on 12/16/15.
 */
public class CarteServer {

    /** The number of threads to allocate to our executor service */
    public static final int POOL_SIZE = 5;

    /** The listener */
    private DatagramChannel channel;

    /** Thread pool */
    private final ExecutorService pool;

    public CarteServer() throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(Network.SERVER_PORT));

        pool = Executors.newFixedThreadPool(POOL_SIZE);
        listen();
    }

    private void listen() {
        System.out.println("Server listening on port: " + Network.SERVER_PORT);
        try {
            while(!Thread.currentThread().isInterrupted()) {

                // Set up the buffer
                ByteBuffer buffer = ByteBuffer.allocate(Network.BUFFER_SIZE);
                buffer.clear();

                // Receive a message
                SocketAddress sender = channel.receive(buffer);

                // Pass to handler
                pool.execute(new MessageHandler(buffer, sender));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
