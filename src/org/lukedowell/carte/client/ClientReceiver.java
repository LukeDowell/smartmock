package org.lukedowell.carte.client;

import org.lukedowell.carte.shared.Network;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

/**
 * Created by ldowell on 12/16/15.
 */
public class ClientReceiver implements Runnable {

    private DatagramChannel channel;

    public ClientReceiver(DatagramChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        System.out.println("Listening for messages...");
        while(!Thread.currentThread().isInterrupted()) {
            try {

                ByteBuffer messageBuffer = ByteBuffer.allocate(Network.BUFFER_SIZE);
                messageBuffer.clear();

                channel.receive(messageBuffer);

                System.out.println("Received message!");
                System.out.println(Arrays.toString(messageBuffer.array()));

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
