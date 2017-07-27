package com.ade.exp.activemq.virtual;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 * Created by liyang on 2017/6/23.
 */
public class Consumer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 连接到ActiveMQ服务器
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // 创建主题
        Queue topicA = session.createQueue("Consumer.A.VirtualTopic.TEST");
        Queue topicB = session.createQueue("Consumer.B.VirtualTopic.TEST");
        // 消费者A组创建订阅
        MessageConsumer consumerA1 = session.createConsumer(topicA);
        consumerA1.setMessageListener(new MessageListener() {
            // 订阅接收方法
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("Received message A1: " + tm.getText()+":"+tm.getStringProperty("property"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        MessageConsumer consumerA2 = session.createConsumer(topicA);
        consumerA2.setMessageListener(new MessageListener() {
            // 订阅接收方法
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("Received message A2: " + tm.getText()+":"+tm.getStringProperty("property"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //消费者B组创建订阅
        MessageConsumer consumerB1 = session.createConsumer(topicB);
        consumerB1.setMessageListener(new MessageListener() {
            // 订阅接收方法
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("Received message B1: " + tm.getText()+":"+tm.getStringProperty("property"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        MessageConsumer consumerB2 = session.createConsumer(topicB);
        consumerB2.setMessageListener(new MessageListener() {
            // 订阅接收方法
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("Received message B2: " + tm.getText()+":"+tm.getStringProperty("property"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (Consumer.class) {
            Consumer.class.wait();
        }
        session.close();
        connection.close();
    }
}
