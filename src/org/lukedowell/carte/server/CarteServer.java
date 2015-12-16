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
public class CarteServer implements MessageListener{

    /** The number of threads to allocate to our executor service */
    public static final int POOL_SIZE = 5;

    /** The maximum number of bytes our channel can receive at once */
    public static final int BUFFER_SIZE = 128;

    /** The listener */
    private DatagramChannel channel;

    /** Thread pool */
    private final ExecutorService pool;

    private Collection<SocketAddress> clients;

    public CarteServer() throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(Network.SERVER_PORT));

        pool = Executors.newFixedThreadPool(POOL_SIZE);

        clients = new ArrayList<>();

        listen();
    }

    private void listen() {
        System.out.println("Server listening on port: " + Network.SERVER_PORT);
        try {
            while(!Thread.currentThread().isInterrupted()) {

                // Set up the buffer
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.clear();

                // Receive a message
                SocketAddress sender = channel.receive(buffer);

                // If we haven't registered this person, do so
                if(!clients.contains(sender)) {
                    clients.add(sender);
                }

                // Pass to handler
                pool.execute(new MessageHandler(buffer, sender));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                String messageText = ((TextMessage) message).getText();
                ByteBuffer messageBuffer = ByteBuffer.wrap(messageText.getBytes());

                for(SocketAddress client : clients) {
                    channel.send(messageBuffer, client);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
