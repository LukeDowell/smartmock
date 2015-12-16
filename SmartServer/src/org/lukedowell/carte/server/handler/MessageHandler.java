package org.lukedowell.carte.server.handler;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ldowell on 12/16/15.
 */
public class MessageHandler implements Runnable {

    private ByteBuffer message;
    private SocketAddress sender;

    public MessageHandler(ByteBuffer message, SocketAddress sender) {
        this.message = message;
        this.sender = sender;
    }

    @Override
    public void run() {
        System.out.println("Received a message from : " + sender.toString());
        System.out.println("Message: " + message.toString());
    }
}
