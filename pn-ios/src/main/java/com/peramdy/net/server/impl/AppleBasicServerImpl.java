package com.peramdy.net.server.impl;

import com.peramdy.net.KeyStoreManager;
import com.peramdy.net.server.AppleServer;

import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by peramdy on 2017/11/7.
 */
public class AppleBasicServerImpl implements AppleServer {

    private final String password;
    private final String protocolType;
    private final Object keyStore;
    private String proxyHost;
    private int proxyPort;

    public AppleBasicServerImpl(final String password, final String protocolType, final Object keyStore) throws Exception {
        KeyStoreManager.validateKeyStoreParameter(keyStore);
        this.password = password;
        this.protocolType = protocolType;
        this.keyStore = keyStore;
    }

    @Override
    public String getKeyStorePassword() {
        return password;
    }

    @Override
    public String getKeyStoreProtocolType() {
        return protocolType;
    }

    @Override
    public InputStream getKeyStoreStream() throws Exception {
        return KeyStoreManager.keyStoreStream(keyStore);
    }

}
