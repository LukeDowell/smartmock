package org.lukedowell.carte.server.handler;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.lukedowell.carte.shared.MessageBrokerConfiguration;
import org.lukedowell.carte.shared.Network;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by ldowell on 12/16/15.
 */
public class MessageHandler implements Runnable {

    private ByteBuffer message;
    private SocketAddress sender;
    private String messageText;

    public MessageHandler(ByteBuffer message, SocketAddress sender) {
        this.message = message;
        this.sender = sender;
        this.messageText = Network.unpackageCommand(message.array());
    }

    @Override
    public void run() {
        try {

            ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText(messageText);

            MessageBrokerConfiguration.getProducer().send(textMessage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
