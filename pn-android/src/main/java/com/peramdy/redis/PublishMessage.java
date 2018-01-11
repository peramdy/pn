package com.peramdy.redis;

import com.peramdy.constants.CommConstant;
import redis.clients.jedis.Jedis;

import java.util.Set;

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


    public static void pub(Jedis jedis, String channel, String message) {
        Long txid = jedis.incr(CommConstant.REDIS_MSG_MAX_ID);
        String content = txid + "/" + message;
        Set<String> keys = jedis.smembers(CommConstant.REDIS_CHANNEL_SET);
        for (String key : keys) {
            jedis.rpush(key, content);
        }
        jedis.publish(channel, content);

    }


}
