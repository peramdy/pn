package com.peramdy.net.server.impl;

import com.peramdy.net.server.AppleNotificationServer;
import com.peramdy.net.utils.Constants;

/**
 * Created by peramdy on 2017/11/8.
 */
public class AppleNotificationServerBasicImpl extends AppleBasicServerImpl implements AppleNotificationServer {

    private String host;
    private int port;

    public AppleNotificationServerBasicImpl(Object keyStore, String password, String host, String protocolType, int port) throws Exception {
        super(password, protocolType, keyStore);
        this.host = host;
        this.port = port;
    }


    public AppleNotificationServerBasicImpl(Object keyStore, String password, boolean isPro) throws Exception {
        this(keyStore, password, Constants.KEYSTORE_TYPE_PKCS12,
                isPro ? Constants.PRODUCTION_HOST : Constants.DEVELOPMENT_HOST,
                isPro ? Constants.PRODUCTION_PORT : Constants.DEVELOPMENT_PORT);
    }


    public AppleNotificationServerBasicImpl(Object keyStore, String password, String type, boolean isPro) throws Exception {
        this(keyStore, password, type,
                isPro ? Constants.PRODUCTION_HOST : Constants.DEVELOPMENT_HOST,
                isPro ? Constants.PRODUCTION_PORT : Constants.DEVELOPMENT_PORT);
    }

    @Override
    public String getNotificationHost() {
        return host;
    }

    @Override
    public int getNotificationPort() {
        return port;
    }
}
