package com.peramdy.net;

import com.peramdy.net.device.Device;
import com.peramdy.net.model.Payload;
import com.peramdy.net.model.PushedNotification;
import com.peramdy.net.server.AppleNotificationServer;
import com.peramdy.net.server.ConnectionToNotificationServer;

import javax.net.ssl.SSLSocket;
import java.io.ByteArrayOutputStream;

/**
 * Created by peramdy on 2017/11/8.
 */
public class PushNotificationManager {


    private ConnectionToServer connectionToServer;

    private SSLSocket socket;

    private int sslSocketTimeout = 30 * 1000;

    public void initializeConnection(AppleNotificationServer server) throws Exception {
        this.connectionToServer = new ConnectionToNotificationServer(server);
        this.socket = connectionToServer.getSSLSocket();
    }

    private static byte[] intTo2ByteArray(final int value) {
        final int s1 = (value & 0xFF00) >> 8;
        final int s2 = value & 0xFF;
        return new byte[]{(byte) s1, (byte) s2};
    }


    private void sendNotification(PushedNotification notification, boolean closeAfter) throws Exception {

        Device device = notification.getDevice();
        Payload payload = notification.getPayload();

        final byte[] bytes = getMessage(device.getToken(), payload, notification);

        if (getSslSocketTimeout() > 0) {
            socket.setSoTimeout(getSslSocketTimeout());
        }


    }


    private byte[] getMessage(String deviceToken, Payload payload, PushedNotification message) throws Exception {

        byte[] deviceTokenAsBytes = new byte[deviceToken.length() / 2];

        String upperCasedDeviceToken = deviceToken.toUpperCase();
        int j = 0;

        for (int i = 0; i < upperCasedDeviceToken.length(); i += 2) {
            String t = upperCasedDeviceToken.substring(i, i + 2);
            final int temp = Integer.parseInt(t, 16);
            deviceTokenAsBytes[j++] = (byte) temp;
        }

        final byte[] payloadAsBytes = payload.getPayloadAsBytes();
        final int size = (Byte.SIZE / Byte.SIZE) + (Character.SIZE / Byte.SIZE) +
                deviceTokenAsBytes.length + (Character.SIZE / Byte.SIZE) +
                payloadAsBytes.length;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final byte b = 1;
        baos.write(b);

        final int requestedExpiry = payload.getExpiry();

        if (requestedExpiry <= 0) {
            baos.write(intTo2ByteArray(requestedExpiry));
            message.setExpiry(0L);
        } else {
            final long ctm = System.currentTimeMillis();
            final long expiryTime = requestedExpiry * 1000;
            final Long expirySeconds = (ctm + expiryTime) / 1000L;
            baos.write(intTo2ByteArray(expirySeconds.intValue()));
            message.setExpiry(ctm + expiryTime);
        }

        final int dt = deviceTokenAsBytes.length;
        baos.write(intTo2ByteArray(dt));

        baos.write(deviceTokenAsBytes);

        final int pl = payloadAsBytes.length;
        baos.write(intTo2ByteArray(pl));

        baos.write(payloadAsBytes);
        final byte[] bytes = baos.toByteArray();

        return bytes;
    }

    public int getSslSocketTimeout() {
        return sslSocketTimeout;
    }
}
