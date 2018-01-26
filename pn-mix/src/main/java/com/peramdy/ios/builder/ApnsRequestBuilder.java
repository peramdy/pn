package com.peramdy.ios.builder;

import com.peramdy.ios.comm.JWT;
import com.peramdy.ios.model.Notification;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author peramdy on 2017/7/19.
 */
public class ApnsRequestBuilder {

    private static Logger logger = LoggerFactory.getLogger(ApnsRequestBuilder.class);

    /**
     * MediaType
     */
    private final MediaType mediaType = MediaType.parse("application/json");

    private String apnsAuthKey;
    private String teamId;
    private String keyId;
    private String gateWay;
    private String defaultTopic;

    /**
     * jwtToken
     */
    private String cachedJWTToken = null;

    /**
     * jwtExpirationTime
     */
    private long lastJWTTokenTS = 0;


    /**
     * default constructor
     */
    public ApnsRequestBuilder() {
    }


    /**
     * constructor for jwtToken
     */
    public ApnsRequestBuilder(String apnsAuthKey, String teamId, String keyId, String gateway, String defaultTopic) {
        this.apnsAuthKey = apnsAuthKey;
        this.teamId = teamId;
        this.keyId = keyId;
        this.gateWay = gateway;
        this.defaultTopic = defaultTopic;
    }


    /**
     * constructor for  certification
     */
    public ApnsRequestBuilder(String gateWay, String defaultTopic) {
        this.gateWay = gateWay;
        this.defaultTopic = defaultTopic;
    }


    public ApnsRequestBuilder addApnsAuthKey(String apnsAuthKey) {
        this.apnsAuthKey = apnsAuthKey;
        return this;
    }

    public ApnsRequestBuilder addTeamId(String teamId) {
        this.teamId = teamId;
        return this;
    }

    public ApnsRequestBuilder addKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }


    public Request build(Notification notification) {
        return getRequest(notification);
    }


    /**
     * @param notification
     * @return
     * @description 获取request信息
     */
    protected final Request getRequest(final Notification notification) {
        if (notification == null) {
            return null;
        }

        Request request = null;

        try {
            Request.Builder rb = new Request.Builder();
            rb.url(gateWay + "/3/device/" + notification.getToken());

            if (notification.getPayload() != null) {
                rb.post(new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return mediaType;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) {
                        try {
                            logger.info("notification: " + notification.getPayload());
                            sink.writeUtf8(notification.getPayload());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (notification.getTopic() == null) {
                rb.header("apns-topic", defaultTopic);
                logger.info("apns-topic: " + defaultTopic);
            } else {
                rb.header("apns-topic", notification.getTopic());
                logger.info("apns-topic: " + notification.getTopic());
            }

            if (notification.getCollapseId() != null) {
                rb.header("apns-collapse-id", notification.getCollapseId());
                logger.info("apns-collapse-id: " + notification.getCollapseId());
            }

            if (notification.getExpiration() > -1) {
                rb.header("apns-expiration", String.valueOf(notification.getExpiration()));
                logger.info("apns-expiration: " + notification.getExpiration());
            }

            if (notification.getUuid() != null) {
                rb.header("apns-id", notification.getUuid().toString());
                logger.info("apns-id: " + notification.getUuid());
            }

            /*********token start************/
            if (teamId != null && apnsAuthKey != null && keyId != null) {
                logger.info("teamId: " + teamId + ", apnsAuthKey: " + apnsAuthKey + ", keyId:" + keyId);
                if (cachedJWTToken == null || System.currentTimeMillis() - lastJWTTokenTS > 55 * 60 * 1000) {
                    lastJWTTokenTS = System.currentTimeMillis();
                    cachedJWTToken = JWT.getToken(teamId, keyId, apnsAuthKey);
                    logger.info("cachedJWTToken: " + cachedJWTToken);
                }
                rb.header("authorization", "bearer " + cachedJWTToken);
            }

            rb.header("Content-Type", "application/json");
            rb.header("content-length", notification.getPayload().getBytes("UTF-8").length + "");

            /*********token end************/


            request = rb.build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
