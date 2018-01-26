package com.peramdy.rocketMq;

import com.peramdy.mq.consumer.RocketMqConsumer;
import com.peramdy.mq.producer.RocketMqProducer;
import org.junit.Test;

/**
 * @author peramdy on 2018/1/15.
 */
public class RocketTest {

    @Test
    public void testProducer() {
        RocketMqProducer rocketMqProducer = new RocketMqProducer();
        rocketMqProducer.sendMq();
    }

    @Test
    public void tesConsumerPull() {
        RocketMqConsumer rocketMqConsumer = new RocketMqConsumer();
        rocketMqConsumer.consumeMsgPull();
    }

    @Test
    public void tesConsumerPush() throws Exception {
        RocketMqConsumer rocketMqConsumer = new RocketMqConsumer();
        rocketMqConsumer.consumeMsgPush();
        Thread.sleep(10000);
    }

}
