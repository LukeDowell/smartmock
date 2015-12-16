package org.lukedowell.carte.shared;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ldowell on 12/16/15.
 */
public class MessageBrokerConfiguration {

    public static Session getSession() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Network.MESSAGE_HOST);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static MessageProducer getProducer() throws JMSException {
        Session session = getSession();
        Destination dest = session.createQueue(Network.QUEUE_NAME);

        return session.createProducer(dest);
    }

    public static MessageConsumer getConsumer() throws JMSException {
        Session session = getSession();
        Destination dest = session.createQueue(Network.QUEUE_NAME);
        return session.createConsumer(dest);
    }
}
