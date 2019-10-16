package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {

    public static final String ACTIVEMQ_URL = "nio://192.168.119.128:61618";
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "Transport";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("******消息发布到MQ完成");
    }
}
