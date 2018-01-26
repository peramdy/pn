/*
 * Copyright (c) 2016, CleverTap
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of CleverTap nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.peramdy.ios.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.UUID;

/**
 * An entity containing the payload and the token.
 */
public class Notification {

    private static Logger logger = LoggerFactory.getLogger(Notification.class);

    private final String payload;//消息如：{"aps":{"alert": {"title":"测试","body":"测试"},"badge":6,"sound": "default"}}
    private final String token;  //device-token
    private final String topic;  //app's topic,apns-topic
    private final String collapseId; //apns-collapse-id
    private final long expiration;    //apns-expiration
    private final Priority priority;
    private final UUID uuid;  //apns-id

    public enum Priority {
        IMMEDIATE(10),
        POWERCONSIDERATION(5);

        private final int code;

        private Priority(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }


    /**
     * Constructs a new Notification with a payload and token.
     *
     * @param payload    The JSON body (which is used for the request)
     * @param token      The device token
     * @param topic      The topic for this notification
     * @param collapseId The collapse ID
     * @param expiration A UNIX epoch date expressed in seconds (UTC)
     * @param priority   The priority of the notification (10 or 5)
     * @param uuid       A canonical UUID that identifies the notification
     */
    protected Notification(String payload, String token, String topic, String collapseId, long expiration, Priority priority, UUID uuid) {
        this.payload = payload;
        this.token = token;
        this.topic = topic;
        this.collapseId = collapseId;
        this.expiration = expiration;
        this.priority = priority;
        this.uuid = uuid;
    }

    /**
     * Retrieves the topic.
     *
     * @return The topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Retrieves the collapseId.
     *
     * @return The collapseId
     */
    public String getCollapseId() {
        return collapseId;
    }

    /**
     * Retrieves the payload.
     *
     * @return The payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Retrieves the token.
     *
     * @return The device token
     */
    public String getToken() {
        return token;
    }

    public long getExpiration() {
        return expiration;
    }

    public Priority getPriority() {
        return priority;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Builds a notification to be sent to APNS.
     */
    public static class Builder {
        private final ObjectMapper mapper = new ObjectMapper();

        private final HashMap<String, Object> root, aps, alert;
        private final String token;
        private String topic = null;
        private String collapseId = null;
        private boolean contentAvailable = false;
        private long expiration = -1; // defaults to -1, as 0 is a valid value (included only if greater than -1)
        private Priority priority;
        private UUID uuid;

        /**
         * Creates a new notification builder.
         *
         * @param token The device token
         */
        public Builder(String token) {
            this.token = token;
            root = new HashMap<>();
            aps = new HashMap<>();
            alert = new HashMap<>();
        }

        public Builder mutableContent(boolean mutable) {
            logger.info("mutableContent: " + mutable);
            if (mutable) {
                aps.put("mutable-content", 1);
            } else {
                aps.remove("mutable-content");
            }

            return this;
        }

        public Builder mutableContent() {
            return this.mutableContent(true);
        }

        public Builder contentAvailable(boolean contentAvailable) {
            logger.info("contentAvailable: " + contentAvailable);
            if (contentAvailable) {
                aps.put("content-available", 1);
            } else {
                aps.remove("content-available");
            }

            this.contentAvailable = contentAvailable;
            return this;
        }

        public Builder contentAvailable() {
            return this.contentAvailable(true);
        }

        public Builder alertBody(String body) {
            logger.info("body: " + body);
            alert.put("body", body);
            return this;
        }

        public Builder alertTitle(String title) {
            alert.put("title", title);
            logger.info("title: " + title);
            return this;
        }

        public Builder sound(String sound) {
            logger.info("sound: " + sound);
            if (sound != null) {
                aps.put("sound", sound);
            } else {
                aps.put("sound", "default");
            }

            return this;
        }

        public Builder category(String category) {
            logger.info("category: " + category);
            if (category != null) {
                aps.put("category", category);
            } else {
                aps.remove("category");
            }
            return this;
        }

        public Builder badge(int badge) {
            logger.info("badge: " + badge);
            aps.put("badge", badge);
            return this;
        }

        public Builder customField(String key, Object value) {
            logger.info("customField#key: " + key + ",#value: " + value);
            root.put(key, value);
            return this;
        }

        public Builder topic(String topic) {
            logger.info("topic: " + topic);
            this.topic = topic;
            return this;
        }

        public Builder collapseId(String collapseId) {
            logger.info("collapseId: " + collapseId);
            this.collapseId = collapseId;
            return this;
        }

        public Builder expiration(long expiration) {
            logger.info("expiration: " + expiration);
            this.expiration = expiration;
            return this;
        }

        public Builder uuid(UUID uuid) {
            logger.info("UUID: " + uuid);
            this.uuid = uuid;
            return this;
        }

        public Builder priority(Priority priority) {
            logger.info("priority: " + priority);
            this.priority = priority;
            return this;
        }

        public int size() {
            try {
                return build().getPayload().getBytes("UTF-8").length;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Builds the notification.
         *
         * @return The notification
         */
        public Notification build() {
            root.put("aps", aps);
            aps.put("alert", alert);

            // Clean up the aps dictionary for content-available notifications
            if (contentAvailable) {
                aps.remove("alert");
                aps.remove("sound");
                aps.remove("badge");
            }

            final String payload;
            try {
                payload = mapper.writeValueAsString(root);
            } catch (JsonProcessingException e) {
                // Should not happen
                throw new RuntimeException(e);
            }
            return new Notification(payload, token, topic, collapseId, expiration, priority, uuid);
        }
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
