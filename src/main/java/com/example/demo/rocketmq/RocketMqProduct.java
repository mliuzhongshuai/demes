package com.example.demo.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author liuzhongshuai
 *         Created on 2018/5/23.
 */
public class RocketMqProduct {


    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr("172.17.2.20:9876");
        producer.setInstanceName("enrol-product");
        // producer.setProducerGroup("enrol-rmq-product");
        producer.setClientIP("localhost");
        producer.setRetryTimesWhenSendAsyncFailed(1);
        //Launch the instance.
        producer.start();
        //Create a message instance, specifying topic, tag and message body.
        Message msg = new Message("TopicTest", "TagA", "Hello RocketMQ ".getBytes(RemotingHelper.DEFAULT_CHARSET));
        //消息延迟发送
        msg.setDelayTimeLevel(2);
        //Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
