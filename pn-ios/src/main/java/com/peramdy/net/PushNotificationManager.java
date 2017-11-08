package com.peramdy.net;

import com.peramdy.net.server.AppleNotificationServer;
import com.peramdy.net.server.ConnectionToNotificationServer;

import javax.net.ssl.SSLSocket;

/**
 * Created by peramdy on 2017/11/8.
 */
public class PushNotificationManager {


    private ConnectionToServer connectionToServer;

    private SSLSocket socket;

    public void initializeConnection(AppleNotificationServer server) throws Exception {
        this.connectionToServer = new ConnectionToNotificationServer(server);
        this.socket = connectionToServer.getSSLSocket();
    }

    public void pushNotification() {

    }

}
