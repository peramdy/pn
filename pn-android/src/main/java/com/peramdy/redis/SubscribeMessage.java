package com.peramdy.redis;

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
}
