package com.peramdy.mq.producer;

import com.peramdy.mq.config.ProduceConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peramdy on 2018/1/13.
 */
public class RocketMqProducer {

    private static Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);

    /**
     * send msg
     */
    public void sendMq() {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.136.130:19876");
        producer.setInstanceName("producer");
        producer.setVipChannelEnabled(false);
        try {
            logger.info("rocketMq start");
            producer.start();
            Message msg = new Message("peramdy", "huahua", "Id188", ("Tuesday - aaa").getBytes());
            SendResult sendResult = producer.send(msg);
            logger.info("rocketMq send msg : " + sendResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }


    /**
     * produce msg
     *
     * @param produceConfig
     */
    public void produceMsg(ProduceConfig produceConfig) {
        DefaultMQProducer producer = new DefaultMQProducer(produceConfig.getProducerGroupName());
        producer.setNamesrvAddr(produceConfig.getNamesrvAddr());
        producer.setInstanceName(produceConfig.getInstanceName());
        producer.setVipChannelEnabled(produceConfig.isVipChannel());
        try {
            logger.info("rocketMq start");
            producer.start();
            Message msg = new Message(produceConfig.getTopic(), produceConfig.getTags(), produceConfig.getKeys(), produceConfig.getBody());
            SendResult sendResult = producer.send(msg);
            logger.info("rocketMq send msg id:" + sendResult.getMsgId() + ", result:" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }


}
