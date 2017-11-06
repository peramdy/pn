package com.peramdy.android.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by peramdy on 2017/7/10.
 */
public class Notification {


//    private final List<String> tokens; //token（registration_ids）
//    private final String topic;        //此参数指定用于确定消息目标的逻辑条件表达式（condition）
//    private final String collapse_key; //此参数用于指定一组可折叠的消息
//    private final String priority;     //设置消息的优先级
//    private final String content_available; //在 Android 中，默认由数据消息唤醒应用
//    private final String time_to_live;  //此参数指定当设备离线时消息在 FCM 存储中保留的时长（以秒为单位）
//    private final String restricted_package_name; //此参数用于指定应用的软件包名称，其注册令牌必须匹配才能接收消息
//    private final String dry_run; //此参数设置为 true 时，开发者可对请求进行测试，而无需实际发送消息，默认值为 false
//    private final String data;    //此参数用于指定消息有效负载的自定义键值对，例如，使用 data:{"score":"3x1"}


//    private final String title; //指示通知标题
//    private final String body;  //指示通知正文
//    private final String icon;  //指示通知图标
//    private final String sound; //指示设备收到通知时要播放的声音
//    private final String tag;   //指示每一条通知是否会导致在 Android 中的通知抽屉式导航栏中产生新条目
//    private final String color; //指示图标颜色
//    private final String click_action;  //指示与用户点击通知相关的操作
//    private final String body_loc_key;  //指示待本地化的正文字符串对应的键
//    private final String body_loc_args; //指示要替换待本地化的正文字符串中的格式说明符的字符串值(可选，字符串形式的 JSON 数组)
//    private final String title_loc_key; //指示待本地化的标题字符串对应的键
//    private final String title_loc_args;//指示要替换待本地化的标题字符串中的格式说明符的字符串值(可选，字符串形式的 JSON 数组)


    private final String payload;  //

    public Notification(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public static class Builder {
        private final ObjectMapper mapper = new ObjectMapper();
        private HashMap<String, Object> root, data, notification;

        public Builder(List<String> tokens) {
            this.root = new HashMap<>();
            this.data = new HashMap<>();
            this.notification = new HashMap<>();
            if (tokens.size() == 1) {
                root.put("to", tokens.get(0));
            } else {
                root.put("registration_ids", tokens.toArray());
            }
        }

        /**
         * @param condition 此参数指定用于确定消息目标的逻辑条件表达式
         * @return
         */
        public Builder condition(String condition) {
            root.put("condition", condition);
            return this;
        }

        /**
         * @param collapse_key 此参数用于指定一组可折叠的消息
         * @return
         */
        public Builder collapse_key(String collapse_key) {
            root.put("collapse_key", collapse_key);
            return this;
        }

        /**
         * @param priority 设置消息的优先级 有效值包括“normal”（普通）和“high”（高）
         * @return
         */
        public Builder priority(String priority) {
            root.put("priority", priority);
            return this;
        }

        /**
         * @param content_available 在 Android 中，默认由数据消息唤醒应用
         * @return
         */
        public Builder content_available(boolean content_available) {
            root.put("content_available", content_available);
            return this;
        }

        /**
         * @param dry_run 此参数设置为 true 时，开发者可对请求进行测试，而无需实际发送消息，默认值为 false
         * @return
         */
        public Builder dry_run(boolean dry_run) {
            root.put("dry_run", dry_run);
            return this;
        }


        /**
         * @param time_to_live 此参数指定当设备离线时消息在 FCM 存储中保留的时长（以秒为单位）
         * @return
         */
        public Builder time_to_live(Long time_to_live) {
            root.put("time_to_live", time_to_live);
            return this;
        }

        /**
         * @param restricted_package_name 此参数用于指定应用的软件包名称，其注册令牌必须匹配才能接收消息
         * @return
         */
        public Builder restricted_package_name(String restricted_package_name) {
            root.put("restricted_package_name", restricted_package_name);
            return this;
        }

        /**
         * @param key
         * @param value
         * @return
         */
        public Builder customFeild(String key, Object value) {
            data.put(key, value);
            return this;
        }

        /**
         * @param title 指示通知标题
         * @return
         */
        public Builder title(String title) {
            notification.put("title", title);
            return this;
        }

        /**
         * @param body 指示通知正文
         * @return
         */
        public Builder body(String body) {
            notification.put("body", body);
            return this;
        }

        /**
         * @param icon 指示通知图标
         * @return
         */
        public Builder icon(String icon) {
            notification.put("icon", icon);
            return this;
        }

        /**
         * @param sound 指示设备收到通知时要播放的声音
         * @return
         */
        public Builder sound(String sound) {
            notification.put("sound", sound);
            return this;
        }

        /**
         * @param tag 指示每一条通知是否会导致在 Android 中的通知抽屉式导航栏中产生新条目
         * @return
         */
        public Builder tag(String tag) {
            notification.put("tag", tag);
            return this;
        }

        /**
         * @param color 指示图标颜色
         * @return
         */
        public Builder color(String color) {
            notification.put("color", color);
            return this;
        }

        /**
         * @param click_action 指示与用户点击通知相关的操作
         * @return
         */
        public Builder click_action(String click_action) {
            notification.put("click_action", click_action);
            return this;
        }

        /**
         * @param body_loc_key 指示待本地化的正文字符串对应的键
         * @return
         */
        public Builder body_loc_key(String body_loc_key) {
            notification.put("body_loc_key", body_loc_key);
            return this;
        }

        /**
         * @param body_loc_args 指示要替换待本地化的正文字符串中的格式说明符的字符串值
         * @return
         */
        public Builder body_loc_args(String body_loc_args) {
            notification.put("body_loc_args", body_loc_args);
            return this;
        }

        /**
         * @param title_loc_key 指示待本地化的标题字符串对应的键
         * @return
         */
        public Builder title_loc_key(String title_loc_key) {
            notification.put("title_loc_key", title_loc_key);
            return this;
        }

        /**
         * @param title_loc_args 指示要替换待本地化的标题字符串中的格式说明符的字符串值
         * @return
         */
        public Builder title_loc_args(String title_loc_args) {
            notification.put("title_loc_args", title_loc_args);
            return this;
        }

        public Notification build() {

            if (data.size() > 0)
                root.put("data", data);
            root.put("notification", notification);
            String payload = null;
            try {
                payload = mapper.writeValueAsString(root);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new Notification(payload);
        }

    }


}
