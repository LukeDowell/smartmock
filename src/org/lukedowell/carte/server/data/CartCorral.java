package org.lukedowell.carte.server.data;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.lukedowell.carte.shared.Command;
import org.lukedowell.carte.shared.Network;

import javax.jms.*;

/**
 * Created by ldowell on 12/16/15.
 */
public class CartCorral implements MessageListener {

    /** The number of 'columns' to initialize our corral */
    public static final int NUM_COLUMNS = 2;

    /** The number of 'boxes' each column will have */
    public static final int NUM_BOXES = 10;

    /** Values for whether or not a cart is in a dock */
    public static final int CHECKED_OUT = 0x00;
    public static final int CHECKED_IN = 0x01;

    /** Our corral */
    private int[][] corral = new int[NUM_COLUMNS][NUM_BOXES];

    /**
     * Creates a new cart corral
     */
    public CartCorral() throws JMSException {
        initializeData();
        establishMessagingConnection();
        System.out.println("Cart Corral Initialized");
    }

    @Override
    public void onMessage(Message message) {
        try {

            if(message instanceof TextMessage) {
                String[] messageText = ((TextMessage) message).getText().split(" ");
                int command = Integer.parseInt(messageText[0]);

                switch (command) {
                    case Command.ALL_DOOR_STATUS:
                        System.out.println("Command : All Door Status received.");
                        break;

                    case Command.SINGLE_DOOR_STATUS:
                        System.out.println("Command : Single Door Status received. Column: " + messageText[1] + " Box: " + messageText[2]);
                        break;

                    default:
                        System.out.println("Unknown command received.");
                }
            }

        } catch(Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Sets up our connection to the message broker
     */
    private void establishMessagingConnection() throws JMSException {
        //TODO: Exception handling
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Network.MESSAGE_HOST);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(Network.QUEUE_NAME);
        session.createConsumer(destination).setMessageListener(this);
    }

    /**
     * Pseudo-randomly initializes our data
     */
    private void initializeData() {
        for(int col = 0; col < NUM_COLUMNS; col++) {
            for(int box = 0; box < NUM_BOXES; box++) {

                if(Math.random() < .66) {
                    // ~2/3 of the time put a cart in the slot
                    corral[col][box] = CHECKED_IN;
                } else {
                    // Otherwise leave it empty
                    corral[col][box] = CHECKED_OUT;
                }
            }
        }
    }
}
