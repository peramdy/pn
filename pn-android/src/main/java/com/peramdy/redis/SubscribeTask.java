package com.peramdy.redis;

import com.peramdy.exception.CustomException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author peramdy
 * @date 2018/1/9.
 */
public class SubscribeTask implements Runnable {

    private String[] channels = null;
    private JedisPubSub jedisPubSub = null;
    @Ignore
    private Jedis jedis;

    /**
     * constructor
     *
     * @param redisConfig
     * @param jedisPubSub
     * @param channels
     */
    public SubscribeTask(RedisConfig redisConfig, JedisPubSub jedisPubSub, String[] channels) {
        if (redisConfig == null) {
            throw new CustomException("redisConfig is null ");
        }
        this.channels = channels;
        this.jedisPubSub = jedisPubSub;
        this.jedis = createJedis(redisConfig);
    }

    /**
     * constructor
     *
     * @param Jedis
     * @param jedisPubSub
     * @param channels
     */
    public SubscribeTask(Jedis Jedis, JedisPubSub jedisPubSub, String[] channels) {
        this.channels = channels;
        this.jedisPubSub = jedisPubSub;
        this.jedis = Jedis;
    }

    private Jedis createJedis(RedisConfig redisConfig) {
        Jedis jedis = new JedisFactory.Builder()
                .url(redisConfig.getRedisUrl())
                .port(redisConfig.getPort())
                .password(redisConfig.getPassword())
                .build();
        return jedis;
    }

    @Override
    public void run() {
//        SubscribeMessage.subscribe(jedis, jedisPubSub, channels);
        SubscribeMessage.sub(jedis, jedisPubSub, channels);
    }
}
