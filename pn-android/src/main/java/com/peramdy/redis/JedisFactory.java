package com.peramdy.redis;

import com.peramdy.constants.CommConstant;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author peramdy
 * @date 2018/1/9.
 */
public class JedisFactory {


    public static class Builder {

        /**
         * redis url
         */
        private String url;

        /**
         * redis port
         */
        private int port;

        /**
         * redis password
         */
        private String password;

        /**
         * add redis url
         *
         * @param url
         * @return
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }


        /**
         * add redis port
         *
         * @param port
         * @return
         */
        public Builder port(int port) {
            this.port = port;
            return this;
        }


        /**
         * add redis password
         *
         * @param password
         * @return
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }


        /**
         * build jedis
         *
         * @return
         */
        public Jedis build() {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxIdle(CommConstant.REDIS_MAX_IDLE);
            poolConfig.setMinIdle(CommConstant.REDIS_MIN_IDLE);
            poolConfig.setMaxTotal(CommConstant.REDIS_MAX_TOTAL);
            JedisPool jedisPool;
            if (password == null) {
                jedisPool = new JedisPool(poolConfig, url, port);
            } else {
                jedisPool = new JedisPool(poolConfig, url, port, CommConstant.REDIS_CONNECT_TIMEOUT, password);
            }
            Jedis jedis = jedisPool.getResource();
            return jedis;
        }
    }


}
