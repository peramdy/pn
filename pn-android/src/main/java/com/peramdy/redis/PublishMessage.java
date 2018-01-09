package com.peramdy.redis;

import redis.clients.jedis.Jedis;

/**
 * @author peramdy
 * @date 2018/1/9.
 */
public class PublishMessage {


    /**
     * publish message to redis
     *
     * @param jedis
     * @param channel
     * @param message
     */
    public static void publish(Jedis jedis, String channel, String message) {
        jedis.publish(channel, message);
    }

    /**
     * publish message to redis
     *
     * @param jedis
     * @param channel
     * @param message
     */
    public static void publish(Jedis jedis, byte[] channel, byte[] message) {
        jedis.publish(channel, message);
    }


}
