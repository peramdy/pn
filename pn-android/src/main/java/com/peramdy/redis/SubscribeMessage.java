package com.peramdy.redis;

import com.peramdy.constants.CommConstant;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author peramdy
 * @date 2018/1/9.
 */
public class SubscribeMessage {

    public static void subscribe(Jedis jedis, JedisPubSub jedisPubSub, String... channels) {
        jedis.subscribe(jedisPubSub, channels);
    }

    public static void subscribe(Jedis jedis, BinaryJedisPubSub binaryJedisPubSub, byte[]... channels) {
        jedis.subscribe(binaryJedisPubSub, channels);
    }


    public static void sub(Jedis jedis, JedisPubSub jedisPubSub, String... channels) {
        for (String channel : channels) {
            String value = CommConstant.REDIS_CHANNEL_CLIENT_ID + "/" + channel;
            jedis.sadd(CommConstant.REDIS_CHANNEL_SET, value);
        }
        jedis.subscribe(jedisPubSub, channels);
    }


}
