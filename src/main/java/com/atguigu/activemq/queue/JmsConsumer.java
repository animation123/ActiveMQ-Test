package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

        public static final String ACTIVEMQ_URL = "nio://192.168.119.128:61608";
//    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "nioauto";

    public static void main(String[] args) throws JMSException, IOException {


        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);

//        /**
//         * 同步阻塞方式(receive())
//         * 订阅者或接收者调用MessageConsumer的receive()方法来接收消息，receive方法在能够接收到消息之前（或超时之前）将一直阻塞
//         */
//        while (true) {
//            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
//            if (null != textMessage) {
//                System.out.println("******消费者接收到消息：" + textMessage.getText());
//            } else {
//                break;
//            }
//        }
//        messageConsumer.close();
//        session.close();
//        connection.close();

        /**
         * 通过监听的方式来消费消息 MessageConsumer messageConsumer = session.createConsumer(queue);
         * 异步非阻塞方式（监听器onMessage()）
         * 订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
         * 当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法
         */
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("******消费者接收到消息broker：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

        /**
         * 1.先生产，只启动1号消费者。问题：1号消费者能消费消息吗？   Y
         *
         * 2.先生产，先启动1号消费者，再启动2号消费者。问题：2号消费者还能消费消息吗？
         *  2.1 1号可以消费          Y
         *  2.2 2号可以消费吗？      N
         *
         * 3.先启动2个消费者，再生产6条消息，请问，消费情况如何？
         *  3.1 2个消费者都有6条
         *  3.2 先到先得，6条全给一个消费者
         *  3.3 一人一半             Y
         */
    }
}
