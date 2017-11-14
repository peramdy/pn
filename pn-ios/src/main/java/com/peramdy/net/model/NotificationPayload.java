package com.peramdy.net.model;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by peramdy on 2017/11/9.
 *
 * @author peramdy
 * @date 2017/11/9.
 */
public class NotificationPayload extends Payload {


    private JSONObject apsDictionary;

    public NotificationPayload() {
        super();
        this.apsDictionary = new JSONObject();
        JSONObject payload = getPayload();
        if (!payload.has("aps")) {
            payload.put("aps", this.apsDictionary);
        }
    }

    /**
     * @return
     */
    private static NotificationPayload complex() {
        NotificationPayload payload = new NotificationPayload();
        return payload;
    }


    /**
     * @param message
     * @return
     * @throws Exception
     * @description Include this key when you want the system to display a standard alert or a banner.The notification settings
     * for your app on the user’s device determine whether an alert or banner is displayed.The preferred value for
     * this key is a dictionary,the keys for which are listed in Table 9-2. If you specify a string as the value of
     * this key, that string is displayed as the message text of the alert or banner.The JSON \U notation is not
     * supported. Put the actual UTF-8 character in the alert text instead
     */
    public static NotificationPayload alert(String message) throws Exception {
        if (message == null) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addAlert(message);
        return payload;
    }


    /**
     * @param sound
     * @return
     * @throws Exception
     * @description Include this key when you want the system to play a sound. The value of this key is the name of a sound
     * file in your app’s main bundle or in the Library/Sounds folder of your app’s data container. If the
     * sound file cannot be found, or if you specify default for the value, the system plays the default alert
     * sound.For details about providing sound files for notifications; see Preparing Custom Alert Sounds.
     */
    public static NotificationPayload sound(String sound) throws Exception {
        if (sound == null) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addSound(sound);
        return payload;
    }


    /**
     * @param count
     * @return
     * @throws Exception
     * @desription Include this key when you want the system to modify the badge of your app icon.
     * If this key is not included in the dictionary, the badge is not changed. To remove the badge,
     * set the value of this key to 0.
     */
    public static NotificationPayload badge(int count) throws Exception {
        if (count < 1) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addBadge(count);
        return payload;

    }

    /**
     * @param available
     * @return
     * @throws Exception
     * @description Include this key with a value of 1 to configure a silent notification. When this key is present,
     * the system wakes up your app in the background and delivers the notification to its app delegate.
     * For information about configuring and handling silent notifications, see Configuring a Silent Notification.
     */
    public static NotificationPayload contentAvailable(int available) throws Exception {
        if (available < 1) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addContentAvailable(available);
        return payload;
    }


    /**
     * @param category
     * @return
     * @throws Exception
     * @description Provide this key with a string value that represents the notification’s type. This value
     * corresponds to the value in the identifier property of one of your app’s registered categories.
     * To learn more about using custom actions, see Configuring Categories and Actionable Notifications.
     */
    public static NotificationPayload category(String category) throws Exception {
        if (category == null) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addCategory(category);
        return payload;
    }


    /**
     * @param threadId
     * @return
     * @throws Exception
     * @description Provide this key with a string value that represents the app-specific identifier for grouping
     * notifications. If you provide a Notification Content app extension, you can use this value to group your
     * notifications together. For local notifications, this key corresponds to the threadIdentifier property of
     * the UNNotificationContent object.
     */
    public static NotificationPayload threadId(String threadId) throws Exception {
        if (threadId == null) {
            throw new Exception();
        }
        NotificationPayload payload = complex();
        payload.addThreadId(threadId);
        return payload;
    }

    /**
     * @param title
     * @throws Exception
     * @description A short string describing the purpose of the notification. Apple Watch displays this string as part
     * of the notification interface. This string is displayed only briefly and should be crafted so that it can be
     * understood quickly. This key was added in iOS 8.2.
     */
    public void addTitle(String title) throws Exception {
        if (StringUtils.isBlank(title)) {
            throw new Exception();
        }
        put("title", title, getOrAddCustomAlert(), true);
    }

    /**
     * @param body
     * @throws Exception
     * @description The text of the alert message.
     */
    public void addBody(String body) throws Exception {
        if (StringUtils.isBlank(body)) {
            throw new Exception();
        }
        put("body", body, getOrAddCustomAlert(), true);
    }

    /**
     * @param titleLocKey
     * @throws Exception
     * @description The key to a title string in the Localizable.strings file for the current localization. The key
     * string can be formatted with %@ and %n$@ specifiers to take the variables specified in the title-loc-args array.
     * See Localizing the Content of Your Remote Notifications for more information. This key was added in iOS 8.2.
     */
    public void addTitleLocKey(String titleLocKey) throws Exception {
        if (StringUtils.isNotBlank(titleLocKey)) {
            put("title-loc-key", titleLocKey, getOrAddCustomAlert(), true);
        }
    }

    /**
     * @param args
     * @throws Exception
     * @description Variable string values to appear in place of the format specifiers in title-loc-key. See Localizing
     * the Content of Your Remote Notifications for more information. This key was added in iOS 8.2.
     */
    public void addTitleLocArgs(List args) throws Exception {
        if (args != null && args.size() > 0) {
            put("title-loc-args", args, getOrAddCustomAlert(), true);
        }
    }

    /**
     * @param actionLocKey
     * @throws Exception
     * @description If a string is specified, the system displays an alert that includes the Close and View buttons.
     * The string is used as a key to get a localized string in the current localization to use for the right button’s
     * title instead of “View”. See Localizing the Content of Your Remote Notifications for more information.
     */
    public void addActionLocKey(String actionLocKey) throws Exception {
        if (StringUtils.isNotBlank(actionLocKey)) {
            put("action-loc-key", actionLocKey, getOrAddCustomAlert(), true);
        }
    }

    /**
     * @param locKey
     * @throws Exception
     * @description A key to an alert-message string in a Localizable.strings file for the current localization
     * (which is set by the user’s language preference). The key string can be formatted with %@ and %n$@ specifiers
     * to take the variables specified in the loc-args array. See Localizing the Content of Your Remote Notifications
     * for more information
     */
    public void addLocKey(String locKey) throws Exception {
        if (StringUtils.isBlank(locKey)) {
            throw new Exception();
        }
        put("loc-key", locKey, getOrAddCustomAlert(), true);
    }


    /**
     * @param args
     * @throws Exception
     * @description Variable string values to appear in place of the format specifiers in loc-key. See Localizing
     * the Content of Your Remote Notifications for more information.
     */
    public void addLocKeyArgs(List args) throws Exception {
        if (args != null && args.size() > 0) {
            put("loc-args", args, getOrAddCustomAlert(), true);
        }
    }


    /**
     * @param launchImage
     * @throws Exception
     * @description The filename of an image file in the app bundle, with or without the filename extension. The image
     * is used as the launch image when users tap the action button or move the action slider. If this property is not
     * specified, the system either uses the previous snapshot, uses the image identified by the UILaunchImageFile key
     * in the app’s Info.plist file, or falls back to Default.png.
     */
    public void addLaunchImage(String launchImage) throws Exception {
        if (StringUtils.isBlank(launchImage)) {
            throw new Exception();
        }
        put("launch-image", launchImage, getOrAddCustomAlert(), true);
    }


    /**
     * @param threadId
     * @throws Exception
     */
    public void addThreadId(String threadId) throws Exception {
        put("thread-id", threadId, this.apsDictionary, false);
    }

    /**
     * @param category
     * @throws Exception
     */
    public void addCategory(String category) throws Exception {
        put("category", category, this.apsDictionary, false);
    }

    /**
     * @param available
     * @throws Exception
     */
    public void addContentAvailable(int available) throws Exception {
        if (available > 1) {
            put("content-available", 1, this.apsDictionary, false);
        }
    }


    /**
     * @param count
     * @throws Exception
     */
    public void addBadge(int count) throws Exception {
        put("badge", count, this.apsDictionary, false);
    }

    /**
     * @param sound
     * @throws Exception
     */
    public void addSound(String sound) throws Exception {
        put("sound", sound, this.apsDictionary, false);
    }


    /**
     * @param messageStr
     * @throws Exception
     */
    public void addAlert(String messageStr) throws Exception {
        getCompatibleProperty("alert", String.class, "", this.apsDictionary);
        put("alert", messageStr, this.apsDictionary, false);
    }


    /**
     * @return
     * @throws Exception
     */
    private JSONObject getOrAddCustomAlert() throws Exception {
        JSONObject alert = getCompatibleProperty("alert", JSONObject.class, "", this.apsDictionary);
        if (alert == null) {
            alert = new JSONObject();
            put("alert", alert, this.apsDictionary, true);
        }
        return alert;
    }


    /**
     * @param propertyName
     * @param expectClass
     * @param exceptionMessage
     * @param dictionary
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T getCompatibleProperty(final String propertyName, Class<T> expectClass,
                                        final String exceptionMessage, final JSONObject dictionary) throws Exception {
        Object propertyValue = null;
        try {
            propertyValue = dictionary.get(propertyName);

        } catch (JSONException e) {
            // e.printStackTrace();

            System.out.println("");
        }

        if (propertyValue == null) {
            return null;
        }
        if (propertyValue.getClass().equals(expectClass)) {
            return (T) propertyValue;
        }

        return null;
    }


}
