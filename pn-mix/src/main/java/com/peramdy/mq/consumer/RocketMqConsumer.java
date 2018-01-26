package com.peramdy.mq.consumer;

import com.peramdy.mq.config.ConsumeConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @author peramdy on 2018/1/13.
 */
public class RocketMqConsumer {

    private static Logger logger = LoggerFactory.getLogger(RocketMqConsumer.class);

    /**
     * consume msg
     */
    public void consumeMsgPush() {

        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumeGroupName");
            consumer.setNamesrvAddr("192.168.136.130:19876");
            consumer.subscribe("peramdy", "huahua");
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(
                        List<MessageExt> list,
                        ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (Message msg : list) {
                        logger.info(msg.toString());
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            logger.info("consume start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * consume msg
     */
    public void consumeMsgPull() {

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("ConsumeGroupName");
        consumer.setNamesrvAddr("192.168.136.130:19876");
        consumer.setInstanceName("consumer");
        consumer.setVipChannelEnabled(false);
        try {
            logger.info("consume msg pull");
            consumer.start();
            Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("peramdy");
            for (MessageQueue queue : messageQueues) {
                logger.info(queue.getTopic());
                logger.info(queue.getBrokerName());
            }

            consumer.registerMessageQueueListener("peramdy", new MessageQueueListener() {
                @Override
                public void messageQueueChanged(String s, Set<MessageQueue> queuesAll, Set<MessageQueue> smqDivided) {
                    System.out.println(s);
                    for (MessageQueue msg : queuesAll) {
                        logger.info("msg topic:" + msg.getTopic() + " ,brokerName: " + msg.getBrokerName() + " , queueId" + msg.getQueueId());
                    }

                    for (MessageQueue msg : smqDivided) {
                        logger.info("msg2 topic:" + msg.getTopic() + " ,brokerName: " + msg.getBrokerName() + " , queueId" + msg.getQueueId());
                    }

                    logger.info(queuesAll.size() + "");
                    logger.info(smqDivided.size() + "");
                }
            });
            logger.info("consume end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.shutdown();
        }
    }


    /**
     * consume msg push
     *
     * @param consumeConfig
     */
    public void consumeMsgPush(ConsumeConfig consumeConfig) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumeConfig.getConsumerGroupName());
        consumer.setNamesrvAddr(consumeConfig.getNamesrvAddr());
        consumer.setInstanceName(consumeConfig.getInstanceName());
        consumer.setVipChannelEnabled(consumeConfig.isVipChannel());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> list,
                    ConsumeConcurrentlyContext context) {
                for (Message msg : list) {
                    byte[] bytes = msg.getBody();
                    String body = new String(bytes);
                    logger.info("msg body :" + body);
                    logger.info(msg.toString());
                }
                logger.info("consume <push>msg end");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.subscribe(consumeConfig.getTopic(), consumeConfig.getTags());
            consumer.start();
            logger.info("consume <push>msg start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    /**
     * consume msg pull
     *
     * @param consumeConfig
     */
    public void consumeMsgPull(ConsumeConfig consumeConfig) {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(consumeConfig.getConsumerGroupName());
        consumer.setNamesrvAddr(consumeConfig.getNamesrvAddr());
        consumer.setInstanceName(consumeConfig.getInstanceName());
        consumer.setVipChannelEnabled(consumeConfig.isVipChannel());
        try {
            consumer.start();
            logger.info("consume <pull>msg start");
            Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(consumeConfig.getTopic());
            for (MessageQueue queue : messageQueues) {
                logger.info(queue.getTopic());
                logger.info(queue.getBrokerName());
            }
            consumer.registerMessageQueueListener(consumeConfig.getTopic(), new MessageQueueListener() {
                @Override
                public void messageQueueChanged(String s, Set<MessageQueue> queuesAll, Set<MessageQueue> smqDivided) {
                    logger.info(queuesAll.size() + "");
                    logger.info(smqDivided.size() + "");
                }
            });
            logger.info("consume <pull>msg end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.shutdown();
        }
    }


    public static void main(String[] args) {
        RocketMqConsumer consumer = new RocketMqConsumer();
//
        ConsumeConfig config = new ConsumeConfig();
//        config.setConsumerGroupName("hello");
//        config.setInstanceName("test");
//        config.setNamesrvAddr("192.168.136.130:19876");
//        config.setTopic("peramdy");
//        config.setTags("huahua");
//        consumer.consumeMsgPush(config);
        consumer.consumeMsgPush();
    }
}


