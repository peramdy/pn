package com.peramdy.redis;

import com.peramdy.exception.CustomException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import redis.clients.jedis.Jedis;

/**
 * @author peramdy
 * @date 2018/1/9.
 */
public class PublishTask implements Runnable {


    @Ignore
    private Jedis jedis;
    /**
     * channel
     */
    private String channel;

    /**
     * message
     */
    private String message;

    /**
     * constructor
     *
     * @param config
     * @param channel
     * @param message
     */
    public PublishTask(RedisConfig config, String channel, String message) {
        if (config == null) {
            throw new CustomException("redisConfig is null");
        }
        this.channel = channel;
        this.message = message;
        this.jedis = createJedis(config);
    }

    /**
     * create jedis
     *
     * @param redisConfig
     * @return
     */
    private Jedis createJedis(RedisConfig redisConfig) {
        Jedis jedis = new JedisFactory.Builder()
                .url(redisConfig.getRedisUrl())
                .port(redisConfig.getPort())
                .password(redisConfig.getPassword())
                .build();
        return jedis;
    }


    /**
     * publish message
     */
    @Override
    public void run() {
        PublishMessage.publish(jedis, channel, message);
    }
}
