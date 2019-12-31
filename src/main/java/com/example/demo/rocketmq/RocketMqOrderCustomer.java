package com.example.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 有序消息消费者
 * @author liuzhongshuai
 *         Created on 2018/5/24.
 */
public class RocketMqOrderCustomer {


    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.setVipChannelEnabled(false);
        consumer.setNamesrvAddr("172.17.2.20:9876");
        consumer.setInstanceName("enrol-product");
        consumer.setClientIP("localhost");

        consumer.subscribe("TopicTest", "TagA || TagC || TagD");
        //保证一个线程消费
        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                boolean error=true;
                if(error==true){
                    //等待3秒 ps:模拟异常重试
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
