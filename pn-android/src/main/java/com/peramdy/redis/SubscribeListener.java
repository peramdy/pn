package com.peramdy.redis;

import com.peramdy.constants.CommConstant;
import com.peramdy.model.Notification;
import com.peramdy.utils.SerializationUtils;
import okhttp3.OkHttpClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author peramdy
 * @date 2018/1/9
 */
public class SubscribeListener extends JedisPubSub {


    private OkHttpClient client;
    private String authKey;
    private HandlerRedis handlerRedis;

    public SubscribeListener(OkHttpClient.Builder builder, String authKey, Jedis jedis) {
        this.client = builder.build();
        this.authKey = authKey;
        this.handlerRedis = new HandlerRedis(jedis);
    }

    public SubscribeListener() {
    }

    /**
     * listener message
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        handlerRedis.handler(channel, message);
    }


    private void message(String channel, String message) {
        if (CommConstant.REDIS_CHANNEL_ANDROID_ONE.equalsIgnoreCase(channel)) {
            Notification notification = SerializationUtils.deserialize(message.getBytes(), Notification.class);
            System.out.println(notification.toString());
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
        System.out.println(String.format("pattern: %s , channel: %s , message: %s", pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

        handlerRedis.subscribe(channel);

        super.onSubscribe(channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        handlerRedis.unSubscribe(channel);
        super.onUnsubscribe(channel, subscribedChannels);
    }

    class HandlerRedis {
        private Jedis jedis;
        private String clientId = CommConstant.REDIS_CHANNEL_CLIENT_ID;

        public HandlerRedis(Jedis jedis) {
            this.jedis = jedis;
        }

        public void handler(String channel, String message) {

            int index = message.indexOf("/");

            if (index < 0) {
                return;
            }

            Long txid = Long.valueOf(message.substring(0, index));
            String key = clientId + "/" + channel;
            while (true) {
                String lm = jedis.lindex(key, 0);
                if (lm == null) {
                    break;
                }
                int li = lm.indexOf("/");
                if (li < 0) {
                    String result = jedis.lpop(key);
                    if (result == null) {
                        break;
                    }
                    message(channel, message.substring(index + 1));
                    continue;
                }

                Long lxid = Long.valueOf(lm.substring(0, li));
                if (txid > lxid) {
                    jedis.lpop(key);
                    message(channel, message.substring(index + 1));
                    continue;
                } else {
                    break;
                }
            }
        }

        public void subscribe(String channel) {
            String key = clientId + "/" + channel;
            boolean isExist = jedis.sismember(CommConstant.REDIS_CHANNEL_SET, key);
            if (isExist) {
                jedis.sadd(CommConstant.REDIS_CHANNEL_SET, key);
            }
        }

        public void unSubscribe(String channel) {
            String key = clientId + "/" + channel;
            jedis.srem(CommConstant.REDIS_CHANNEL_SET, key);
            jedis.del(channel);
        }

    }


}
