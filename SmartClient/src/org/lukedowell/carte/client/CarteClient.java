package org.lukedowell.carte.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ldowell on 12/16/15.
 */
public class CarteClient {

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

    public CarteClient() throws IOException {
        channel = DatagramChannel.open();

        pool = Executors.newFixedThreadPool(POOL_SIZE);

        String myMessage = "Hello CarteServer!";
        ByteBuffer messageBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        messageBuffer.clear();
        messageBuffer.put(myMessage.getBytes());
        messageBuffer.flip();

        int numSent = channel.send(messageBuffer, new InetSocketAddress("127.0.0.1", SERVER_PORT));
        System.out.println("Sent " + numSent + " bytes to the server!");
    }
}
