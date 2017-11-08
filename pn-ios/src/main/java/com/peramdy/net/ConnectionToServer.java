package com.peramdy.net;

import com.peramdy.net.server.AppleServer;
import com.peramdy.net.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.KeyStore;

/**
 * @author peramdy
 * @date 2017/11/7.
 */
public abstract class ConnectionToServer {

    private static Logger logger = LoggerFactory.getLogger(ConnectionToServer.class);


    private KeyStore keyStore;

    private AppleServer server;

    private String ALGORITHM = KeyManagerFactory.getDefaultAlgorithm();

    private SSLSocketFactory sslSocketFactory;

    protected ConnectionToServer(final AppleServer server) throws Exception {
        this.server = server;
        this.keyStore = KeyStoreManager.getKeyStore(server);
    }

    private KeyStore getKeyStore() {
        return keyStore;
    }

    /**
     *
     * @return
     */
    public abstract String getServerHost();

    /**
     *
     * @return
     */
    public abstract int getServerPort();

    public AppleServer getServer() {
        return server;
    }

    private SSLSocketFactory createSSLSocketFactoryWithTrustManagers(TrustManager[] trustManagers) throws Exception {
        logger.info("Creating sslSocketFactory");

        try {
            KeyStore keyStore = getKeyStore();
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
            char[] password = KeyStoreManager.getPasswordForSSL(server);
            kmf.init(keyStore, password);

            SSLContext sslContext = SSLContext.getInstance(Constants.PROTOCOL);
            sslContext.init(kmf.getKeyManagers(), trustManagers, null);

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SSLSocketFactory creteSSLSocketFactory() throws Exception {
        return createSSLSocketFactoryWithTrustManagers(new TrustManager[]{new TrustManagerServer()});
    }

    private SSLSocketFactory getSslSocketFactory() throws Exception {
        if (sslSocketFactory == null) {
            return creteSSLSocketFactory();
        }
        return sslSocketFactory;
    }

    public SSLSocket getSSLSocket() throws Exception {
        SSLSocketFactory sslSocketFactory = getSslSocketFactory();
        logger.info("Creating sslSocket");
        return (SSLSocket) sslSocketFactory.createSocket(getServerHost(), getServerPort());
    }

}
