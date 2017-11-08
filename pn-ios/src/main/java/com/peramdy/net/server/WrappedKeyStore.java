package com.peramdy.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author peramdy
 * @Date 2017/11/7
 */
public class WrappedKeyStore extends InputStream {

    private KeyStore keyStore;

    public WrappedKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
