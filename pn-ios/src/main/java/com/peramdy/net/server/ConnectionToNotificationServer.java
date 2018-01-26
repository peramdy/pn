package com.peramdy.net.server;

import com.peramdy.net.ConnectionToServer;

/**
 * @author peramdy on 2017/11/8.
 */
public class ConnectionToNotificationServer extends ConnectionToServer {


    public ConnectionToNotificationServer(AppleNotificationServer server) throws Exception {
        super(server);
    }

    @Override
    public String getServerHost() {
        return ((AppleNotificationServer) getServer()).getNotificationHost();
    }

    @Override
    public int getServerPort() {
        return ((AppleNotificationServer) getServer()).getNotificationPort();
    }
}
