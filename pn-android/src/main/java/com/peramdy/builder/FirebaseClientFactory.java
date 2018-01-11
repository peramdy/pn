package com.peramdy.builder;

import com.peramdy.client.FirebaseClient;
import com.peramdy.client.impl.SyncOkHttpClient;
import com.peramdy.constants.CommConstant;
import com.peramdy.exception.CustomException;
import com.peramdy.redis.JedisFactory;
import com.peramdy.redis.RedisConfig;
import com.peramdy.redis.SubscribeListener;
import com.peramdy.redis.SubscribeTask;
import jdk.nashorn.internal.ir.annotations.Ignore;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @author peramdy
 * @date 2018/1/8.
 */
public class FirebaseClientFactory {


    /**
     * connect timeout
     */
    public static int CONNECT_TIMEOUT = 10;

    /**
     * write timeout
     */
    public static int WRITE_TIMEOUT = 10;

    /**
     * read timeout
     */
    public static int READ_TIMEOUT = 10;

    /**
     * maxIdleConnections
     */
    private static int MAX_IDLE_CONNECTIONS = 10;

    /**
     * keepAliveDuration
     */
    private static int KEEP_ALIVE_DURATION = 10;


    public static class Builder {

        /**
         * your server key
         */
        private String authKey;

        private int connectTime;

        private int writeTimeout;

        private int readTimeout;

        private int maxIdleConnections;

        private int keepAliveDuration;

        private String redisUrl;

        private int redisPort;

        private String redisPassword;

        @Ignore
        private RedisConfig config;

        /**
         * constructor
         *
         * @param authKey
         */
        public Builder(String authKey) {
            if (authKey == null || StringUtils.isBlank(authKey)) {
                throw new CustomException("authKey is null");
            }
            this.authKey = authKey;
            this.connectTime = CONNECT_TIMEOUT;
            this.writeTimeout = WRITE_TIMEOUT;
            this.readTimeout = READ_TIMEOUT;
            this.maxIdleConnections = MAX_IDLE_CONNECTIONS;
            this.keepAliveDuration = KEEP_ALIVE_DURATION;
        }


        /**
         * connectTime
         *
         * @param connectTime
         * @return
         */
        public Builder connectTime(int connectTime) {
            if (connectTime < 1) {
                this.connectTime = CONNECT_TIMEOUT;
            } else {
                this.connectTime = connectTime;
            }
            return this;
        }

        /**
         * writeTimeout
         *
         * @param writeTimeout
         * @return
         */
        public Builder writeTimeout(int writeTimeout) {
            if (writeTimeout < 1) {
                this.writeTimeout = WRITE_TIMEOUT;
            } else {
                this.writeTimeout = writeTimeout;
            }
            return this;
        }

        /**
         * readTimeout
         *
         * @param readTimeout
         * @return
         */
        public Builder readTimeout(int readTimeout) {
            if (readTimeout < 1) {
                this.readTimeout = READ_TIMEOUT;
            } else {
                this.readTimeout = readTimeout;
            }
            return this;
        }

        /**
         * maxIdleConnections
         *
         * @param maxIdleConnections
         * @return
         */
        public Builder maxIdleConnections(int maxIdleConnections) {
            if (maxIdleConnections < 1) {
                this.maxIdleConnections = MAX_IDLE_CONNECTIONS;
            } else {
                this.maxIdleConnections = maxIdleConnections;
            }
            return this;
        }

        /**
         * keepAliveDuration
         *
         * @param keepAliveDuration
         * @return
         */
        public Builder keepAliveDuration(int keepAliveDuration) {
            if (keepAliveDuration < 1) {
                this.keepAliveDuration = KEEP_ALIVE_DURATION;
            } else {
                this.keepAliveDuration = keepAliveDuration;
            }
            return this;
        }

        /**
         * redis url
         *
         * @param redisUrl
         * @return
         */
        public Builder redisUrl(String redisUrl) {
            if (StringUtils.isBlank(redisUrl)) {
                throw new CustomException("redis url is null");
            }
            this.redisUrl = redisUrl;
            return this;
        }


        /**
         * redis port
         *
         * @param redisPort
         * @return
         */
        public Builder redisPort(int redisPort) {
            if (redisPort < 0) {
                throw new CustomException("redis port error");
            }
            this.redisPort = redisPort;
            return this;
        }

        /**
         * redis password
         *
         * @param redisPassword
         * @return
         */
        public Builder redisPassword(String redisPassword) {
            this.redisPassword = redisPassword;
            return this;
        }

        /**
         * create okHttp builder
         *
         * @return
         */
        private OkHttpClient.Builder createBuilder() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTime, TimeUnit.SECONDS);
            builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
            ConnectionPool connectionPool = new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
            builder.connectionPool(connectionPool);
            return builder;
        }

        private Jedis createJedis() {
            Jedis jedis = new JedisFactory.Builder()
                    .url(redisUrl)
                    .port(redisPort)
                    .password(redisPassword)
                    .build();
            return jedis;
        }

        /**
         * build
         *
         * @return
         */
        public FirebaseClient build() {
            FirebaseClient firebaseClient;
            if (StringUtils.isNotBlank(redisUrl) && redisPort > 0) {
                firebaseClient = new SyncOkHttpClient(createBuilder(), authKey, createJedis());
            } else {
                firebaseClient = new SyncOkHttpClient(createBuilder(), authKey);
            }
            return firebaseClient;
        }

        /**
         * @return
         */
        public FirebaseClient buildQueue() {
            FirebaseClient firebaseClient;
            Jedis jedis = createJedis();
            SubscribeListener listener = new SubscribeListener(createBuilder(), authKey, createJedis());
            String[] channels = {CommConstant.REDIS_CHANNEL_ANDROID_ONE};
            SubscribeTask task = new SubscribeTask(jedis, listener, channels);
            Thread t = new Thread(task);
            t.start();

            if (StringUtils.isNotBlank(redisUrl) && redisPort > 0) {
                firebaseClient = new SyncOkHttpClient(createBuilder(), authKey, createJedis());
            } else {
                firebaseClient = new SyncOkHttpClient(createBuilder(), authKey);
            }
            return firebaseClient;
        }

    }


}
