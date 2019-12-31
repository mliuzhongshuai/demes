package com.example.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 并发消费
 * @author liuzhongshuai
 *         Created on 2018/5/23.
 */
public class RocketMqCustomer {



    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setVipChannelEnabled(false);
        consumer.setNamesrvAddr("172.17.2.20:9876");
        consumer.setInstanceName("enrol-product");
        consumer.setClientIP("localhost");
       // consumer.setConsumerGroup("test");
        consumer.setMessageModel(MessageModel.CLUSTERING);//消息模式，集群和广播
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicTest", "TagA || TagC || TagD");


        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //多线程 并发消费
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {

                System.out.println(msgs);
                //重试
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
               // return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }



}
