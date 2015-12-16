package org.lukedowell.carte.server.data;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lukedowell.carte.shared.Command;
import org.lukedowell.carte.shared.Network;

import javax.jms.*;
import java.awt.*;

/**
 * Created by ldowell on 12/16/15.
 */
public class ChaosCart implements Runnable {

    public static final int SLEEP_TIME = 10000;

    private MessageProducer messageProducer;

    private Session session;


    /**
     * Creates a new instance of our chaos monkey. Establishes a message producer.
     *
     */
    public ChaosCart() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Network.MESSAGE_HOST);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination dest = session.createQueue(Network.QUEUE_NAME);

        this.messageProducer = session.createProducer(dest);
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    @Override
    public void run() {
        System.out.println("Starting chaos cart...");
        while(!Thread.currentThread().isInterrupted()) {
            try {
                if(Math.random() < .5) {
                    TextMessage textMessage = session.createTextMessage();
                    textMessage.setText("1");

                    messageProducer.send(textMessage);

                    System.out.println("Message sent");
                    Thread.sleep(SLEEP_TIME);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
