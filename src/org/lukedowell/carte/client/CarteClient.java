package org.lukedowell.carte.client;

import org.lukedowell.carte.shared.Command;
import org.lukedowell.carte.shared.Network;

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

    /** The number of threads to allocate to our executor service */
    public static final int POOL_SIZE = 5;

    /** The maximum number of bytes our channel can receive at once */
    public static final int BUFFER_SIZE = 128;

    /** The listener */
    private DatagramChannel channel;

    /** Thread pool */
    private final ExecutorService pool;

    /** Server's host address */
    private InetSocketAddress hostAddress;

    public CarteClient() throws IOException {
        channel = DatagramChannel.open();
        pool = Executors.newFixedThreadPool(POOL_SIZE);
        hostAddress = new InetSocketAddress(Network.SERVER_HOST, Network.SERVER_PORT);

        byte[] message = Network.packageCommand(Command.SINGLE_DOOR_STATUS, 2, 5);
        System.out.println("Sending a message of length: " + send(message));
    }

    /**
     *
     * @param messageArray
     * @return
     * @throws IOException
     */
    public int send(byte[] messageArray) throws IOException {
        ByteBuffer messageBuffer = ByteBuffer.wrap(messageArray);
        return channel.send(messageBuffer, hostAddress);
    }
}
