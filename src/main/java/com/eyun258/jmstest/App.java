package com.eyun258.jmstest;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *http://127.0.0.1:8161/admin    admin/admin
 */
public class App 
{
    public static void main( String[] args )
    {
    	queueMessageRecive1();
    	queueMessageRecive2();
    	
    	queueMessageSend1();
    	queueMessageSend2();
    	
//    	topicMessageRecive();
//    	topicMessageRecive();
//    	topicMessageRecive();
//    	topicMessageRecive();
//    	
//    	topicMessageSend();
//    	topicMessageSend();
    	
    	
    }
    public static void queueMessageSend1()
    {
    	
    	System.out.println( "Start send!" );
    	ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Queue destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
        
//        connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList("org.apache.activemq.test,org.apache.camel.test,com.eyun258.jmstest,java.lang.Intege".split(","))));
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数是是否是事务型消息，设置为true,第二个参数无效
            //第二个参数是
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            //时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            //待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-queue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢
            MqBean bean = new MqBean();
            bean.setAge(13);
            for(int i=0;i<5;i++){
                bean.setName("小黄"+i+"=====SEND01");
                producer.send(session.createObjectMessage(bean));
            }
            producer.close();
            System.out.println("呵呵");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    	
    }
    public static void queueMessageSend2()
    {
    	
    	System.out.println( "Start send!" );
    	ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Queue destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
        
//        connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList("org.apache.activemq.test,org.apache.camel.test,com.eyun258.jmstest,java.lang.Intege".split(","))));
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数是是否是事务型消息，设置为true,第二个参数无效
            //第二个参数是
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            //时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            //待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-queue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢
            MqBean bean = new MqBean();
            bean.setAge(13);
            for(int i=0;i<5;i++){
                bean.setName("小黄"+i+"=====SEND02");
                producer.send(session.createObjectMessage(bean));
            }
            producer.close();
            System.out.println("呵呵");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    	
    }
    public static void queueMessageRecive1()
    {
    	System.out.println( "Start recive!" );
    	ActiveMQConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接  
        Connection connection = null;
        // Session： 一个发送或接收消息的线程  
        Session session;
        // Destination ：消息的目的地;消息发送给谁.  
        Queue destination;
        // 消费者，消息接收者  
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
//        connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList("org.apache.activemq.test,org.apache.camel.test,com.eyun258.jmstest,java.lang.Integer".split(","))));
        connectionFactory.setTrustAllPackages(true);
        try {
            // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();
            // 启动  
            connection.start();
            // 获取操作连接  
            //这个最好还是有事务
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("test-queue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {

				public void onMessage(Message arg0) {
					// TODO Auto-generated method stub
					try {
						Object o=((ObjectMessage)arg0).getObject();
                        MqBean bean = (MqBean)o;
                        System.out.println(bean);
                        if (null != arg0) {
                            System.out.println("收到消息==R01" + bean.getName());
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    	System.out.println(e.getLocalizedMessage());
                    }
					
				}
				
				
               
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void queueMessageRecive2()
    {
    	System.out.println( "Start recive!" );
    	ActiveMQConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接  
        Connection connection = null;
        // Session： 一个发送或接收消息的线程  
        Session session;
        // Destination ：消息的目的地;消息发送给谁.  
        Queue destination;
        // 消费者，消息接收者  
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
//        connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList("org.apache.activemq.test,org.apache.camel.test,com.eyun258.jmstest,java.lang.Integer".split(","))));
        connectionFactory.setTrustAllPackages(true);
        try {
            // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();
            // 启动  
            connection.start();
            // 获取操作连接  
            //这个最好还是有事务
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("test-queue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {

				public void onMessage(Message arg0) {
					// TODO Auto-generated method stub
					try {
						Object o=((ObjectMessage)arg0).getObject();
                        MqBean bean = (MqBean)o;
                        System.out.println(bean);
                        if (null != arg0) {
                            System.out.println("收到消息==R01" + bean.getName());
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    	System.out.println(e.getLocalizedMessage());
                    }
					
				}
				
				
               
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void topicMessageSend()
    {
    	System.out.println("Start topic send");
    	ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Topic destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createTopic("topic01");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢
            MqBean bean = new MqBean();
            bean.setAge(13);
            for(int i=0;i<100;i++){
//                Thread.sleep(1000);
                bean.setName("小黄"+i);
                producer.send(session.createObjectMessage(bean));
            }
            producer.close();
            System.out.println("呵呵");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void topicMessageRecive()
    {
    	System.out.println("Start topic recive");
    	ActiveMQConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接  
        Connection connection = null;
        // Session： 一个发送或接收消息的线程  
        Session session;
        // Destination ：消息的目的地;消息发送给谁.  
        Topic destination;
        // 消费者，消息接收者  
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();
            // 启动  
            connection.start();
            // 获取操作连接  
            //这个最好还是有事务
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createTopic("topic01");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
              //  @Override
                public void onMessage(Message message) {
                    try {
                        MqBean bean = (MqBean) ((ObjectMessage)message).getObject();
                        System.out.println(bean);
                        if (null != message) {
                            System.out.println("收到消息" + bean.getName());
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    	System.out.println(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
