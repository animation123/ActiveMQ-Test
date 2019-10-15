package com.atguigu.boot.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_Consumer {

    @JmsListener(destination = "${myTopic}")
    public void receive(TextMessage text) throws JMSException {
        System.out.println("消费者收到订阅的主题：" + text.getText());
    }

}
