package com.peramdy.net.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramdy.net.exception.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peramdy on 2018/1/11.
 */
public class NotificationPayload2 {

    private JSONObject payload;

    public NotificationPayload2(JSONObject payload) {
        this.payload = payload;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public static class Builder {

        private Map<String, Object> root;
        private Map<String, Object> aps;
        private Map<String, Object> alert;
        private Map<String, Object> custom;

        public Builder() {
            this.root = new HashMap<>();
            this.aps = new HashMap<>();
            this.alert = new HashMap<>();
            this.custom = new HashMap<>();
        }

        /**
         * Include this key when you want the system to display a standard alert or a banner. The notification settings
         * for your app on the user’s device determine whether an alert or banner is displayed.
         * <p>
         * The preferred value for this key is a dictionary, the keys for which are listed in Table 9-2. If you specify
         * a string as the value of this key, that string is displayed as the message text of the alert or banner.
         * <p>
         * The JSON \U notation is not supported. Put the actual UTF-8 character in the alert text instead
         *
         * @param alert
         * @return
         */
        public Builder apsAlert(String alert) {
            if (StringUtils.isBlank(alert)) {
                throw new CustomException("alert is null");
            }
            aps.put("alert", alert);
            return this;
        }

        /**
         * Include this key when you want the system to modify the badge of your app icon.
         * <p>
         * If this key is not included in the dictionary, the badge is not changed. To remove the badge, set the value
         * of this key to 0.
         *
         * @param badge
         * @return
         */
        public Builder apsBadge(int badge) {
            if (badge < 1) {
                throw new CustomException("badge is error");
            }
            aps.put("badge", badge);
            return this;
        }

        /**
         * Include this key when you want the system to play a sound. The value of this key is the name of a sound
         * file in your app’s main bundle or in the Library/Sounds folder of your app’s data container. If the sound
         * file cannot be found, or if you specify defaultfor the value, the system plays the default alert sound.
         * <p>
         * For details about providing sound files for notifications; see Preparing Custom Alert Sounds.
         *
         * @param sound
         * @return
         */
        public Builder apsSound(String sound) {
            if (StringUtils.isBlank(sound)) {
                throw new CustomException("sound is null");
            }
            aps.put("sound", sound);
            return this;
        }

        /**
         * Include this key with a value of 1 to configure a background update notification. When this key is present,
         * the system wakes up your app in the background and delivers the notification to its app delegate. For
         * information about configuring and handling background update notifications, see Configuring a Background
         * Update Notification.
         *
         * @param contentAvailable
         * @return
         */
        public Builder apsContentAvailable(int contentAvailable) {
            aps.put("content-available", contentAvailable);
            return this;
        }

        /**
         * Provide this key with a string value that represents the notification’s type. This value corresponds to the
         * value in the identifier property of one of your app’s registered categories. To learn more about using custom
         * actions, see Configuring Categories and Actionable Notifications.
         *
         * @param category
         * @return
         */
        public Builder apsCategory(String category) {
            if (StringUtils.isBlank(category)) {
                throw new CustomException("category is null");
            }
            aps.put("category", category);
            return this;
        }

        /**
         * Provide this key with a string value that represents the app-specific identifier for grouping notifications.
         * If you provide a Notification Content app extension, you can use this value to group your notifications
         * together. For local notifications, this key corresponds to the threadIdentifier property of the
         * UNNotificationContent object.
         *
         * @param threadId
         * @return
         */
        public Builder apsThreadId(String threadId) {
            if (StringUtils.isBlank(threadId)) {
                throw new CustomException("threadId is null");
            }
            aps.put("thread-id", threadId);
            return this;
        }

        /**
         * A short string describing the purpose of the notification. Apple Watch displays this string as part of the
         * notification interface. This string is displayed only briefly and should be crafted so that it can be
         * understood quickly. This key was added in iOS 8.2.
         *
         * @param title
         * @return
         */
        public Builder alertTitle(String title) {
            if (StringUtils.isBlank(title)) {
                throw new CustomException("title is null");
            }
            alert.put("title", title);
            return this;
        }

        /**
         * The text of the alert message.
         *
         * @param body
         * @return
         */
        public Builder alertBody(String body) {
            if (StringUtils.isBlank(body)) {
                throw new CustomException("body is null");
            }
            alert.put("body", body);
            return this;
        }

        /**
         * The key to a title string in the Localizable.strings file for the current localization. The key string can be
         * formatted with %@ and %n$@ specifiers to take the variables specified in the title-loc-args array. See
         * Localizing the Content of Your Remote Notifications for more information. This key was added in iOS 8.2.
         *
         * @param titleLocKey
         * @return
         */
        public Builder alertTitleLocKey(String titleLocKey) {
            alert.put("title-loc-key", titleLocKey);
            return this;
        }


        /**
         * Variable string values to appear in place of the format specifiers in title-loc-key. See Localizing the
         * Content of Your Remote Notifications for more information. This key was added in iOS 8.2
         *
         * @param titleLocArgs
         * @return
         */
        public Builder alertTitleLocArgs(String titleLocArgs) {
            alert.put("title-loc-args", titleLocArgs);
            return this;
        }

        /**
         * If a string is specified, the system displays an alert that includes the Close and View buttons. The string
         * is used as a key to get a localized string in the current localization to use for the right button’s title
         * instead of “View”. See Localizing the Content of Your Remote Notifications for more information
         *
         * @param actionLocKey
         * @return
         */
        public Builder alertActionLocKey(String actionLocKey) {
            alert.put("action-loc-key", actionLocKey);
            return this;
        }

        /**
         * A key to an alert-message string in a Localizable.strings file for the current localization (which is set by
         * the user’s language preference). The key string can be formatted with %@ and %n$@ specifiers to take the
         * variables specified in the loc-args array. See Localizing the Content of Your Remote Notifications for more
         * information.
         *
         * @param locKey
         * @return
         */
        public Builder alertLocKey(String locKey) {
            if (StringUtils.isBlank(locKey)) {
                throw new CustomException("locKey is null");
            }
            alert.put("loc-key", locKey);
            return this;
        }

        /**
         * Variable string values to appear in place of the format specifiers in loc-key. See Localizing the Content of
         * Your Remote Notifications for more information.
         *
         * @param locArgs
         * @return
         */
        public Builder alertLocArgs(String locArgs) {
            if (StringUtils.isBlank(locArgs)) {
                throw new CustomException("locArgs is null");
            }
            alert.put("loc-args", locArgs);
            return this;
        }

        /**
         * The filename of an image file in the app bundle, with or without the filename extension. The image is used
         * as the launch image when users tap the action button or move the action slider. If this property is not
         * specified, the system either uses the previous snapshot, uses the image identified by the UILaunchImageFile
         * key in the app’s Info.plist file, or falls back to Default.png
         *
         * @param launchImage
         * @return
         */
        public Builder alertLaunchImage(String launchImage) {
            if (StringUtils.isBlank(launchImage)) {
                throw new CustomException("launchImage is null");
            }
            alert.put("launch-image", launchImage);
            return this;
        }

        /**
         * custom dictionary
         *
         * @param key
         * @param value
         * @return
         */
        public Builder addCustomDictionary(String key, Object value) {
            root.put(key, value);
            return this;
        }

        /**
         * build
         *
         * @return
         */
        public NotificationPayload2 build() {
            aps.put("alert", alert);
            root.put("aps", aps);
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = null;
            try {
                jsonStr = mapper.writeValueAsString(root);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            /*String jsonStr = JSON.toJSONString(root);*/
            int currentPayloadSize = getCurrentPayloadSize(jsonStr);
            int maximumPayloadSize = getMaximumPayloadSize();
            if (currentPayloadSize > maximumPayloadSize) {
                throw new CustomException("Payload Is OutOfSizeError ");
            }
            NotificationPayload2 notification = new NotificationPayload2(JSON.parseObject(jsonStr));
            return notification;
        }


        /**
         * getCurrentPayloadSize
         *
         * @param strPayload
         * @return
         */
        private int getCurrentPayloadSize(String strPayload) {
            if (strPayload == null) {
                return 0;
            }
            int len = strPayload.getBytes().length;
            return len;
        }

        /**
         * getMaximumPayloadSize
         *
         * @return
         */
        private int getMaximumPayloadSize() {
            return Integer.MAX_VALUE;
        }
    }

}
