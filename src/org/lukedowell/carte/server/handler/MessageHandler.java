package org.lukedowell.carte.server.handler;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lukedowell.carte.shared.Network;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
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
        try {
            System.out.println("Received a message from: " + sender.toString());
            System.out.println(Network.unpackageCommand(message.array()));

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Network.MESSAGE_HOST);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(Network.QUEUE_NAME);
            // Blasodarawraggaararadgararg
            session.createProducer(destination).send(session.createTextMessage(Network.unpackageCommand(message.array())));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
