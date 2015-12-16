package org.lukedowell.carte.server.data;


import org.lukedowell.carte.shared.MessageBrokerConfiguration;
import org.lukedowell.carte.shared.Network;

import javax.jms.*;

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
        session = MessageBrokerConfiguration.getSession();
        Destination dest = session.createQueue(Network.QUEUE_NAME);
        messageProducer = session.createProducer(dest);
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

                    Thread.sleep(SLEEP_TIME);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
