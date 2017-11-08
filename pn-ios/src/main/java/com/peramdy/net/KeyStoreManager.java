package com.peramdy.net;

import com.peramdy.net.server.AppleServer;
import com.peramdy.net.server.WrappedKeyStore;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.security.KeyStore;

/**
 * @author peramdy
 * @Date 2017/11/7.
 */
public class KeyStoreManager {


    public KeyStoreManager() {
    }


    public static KeyStore getKeyStore(final AppleServer server) throws Exception {
        return loadKeyStore(server);
    }

    /**
     * @param server
     * @return
     * @throws Exception
     * @description loadKeyStore
     */
    private static synchronized KeyStore loadKeyStore(final AppleServer server) throws Exception {

        if (server == null || server.getKeyStoreStream() == null) {
            return null;
        }

        InputStream inputStream = server.getKeyStoreStream();

        if (inputStream instanceof WrappedKeyStore) {
            return ((WrappedKeyStore) inputStream).getKeyStore();
        }

        KeyStore keyStore = KeyStore.getInstance(server.getKeyStoreProtocolType());
        char[] password = KeyStoreManager.getPasswordForSSL(server);
        keyStore.load(inputStream, password);
        return keyStore;
    }


    /**
     * @param server
     * @return
     * @description string to char array for password
     */
    public static char[] getPasswordForSSL(final AppleServer server) {
        String password = server.getKeyStorePassword();
        if (StringUtils.isBlank(password)) {
            password = "";
        }
        return password.toCharArray();
    }


    /**
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static InputStream keyStoreStream(Object keyStore) throws Exception {
        validateKeyStoreParameter(keyStore);

        if (keyStore instanceof InputStream) {
            return (InputStream) keyStore;
        } else if (keyStore instanceof KeyStore) {
            return new WrappedKeyStore((KeyStore) keyStore);
        } else if (keyStore instanceof String) {
            return new FileInputStream(new File((String) keyStore));
        } else if (keyStore instanceof byte[]) {
            return new ByteArrayInputStream((byte[]) keyStore);
        } else if (keyStore instanceof File) {
            return new FileInputStream((File) keyStore);
        }

        return null;
    }


    /**
     * @param keyStore
     * @throws Exception
     */
    public static void validateKeyStoreParameter(Object keyStore) throws Exception {

        if (keyStore == null) {
            throw new Exception();
        }

        if (keyStore instanceof KeyStore) {
            return;
        }

        if (keyStore instanceof String) {
            validateFileKeyStore(new File((String) keyStore));
            return;
        }

        if (keyStore instanceof File) {
            validateFileKeyStore((File) keyStore);
            return;
        }

        if (keyStore instanceof byte[]) {
            final byte[] bytes = (byte[]) keyStore;
            if (bytes.length == 0) {
                throw new Exception();
            }
            return;
        }
    }


    /**
     * @param keyStore
     * @throws Exception
     */
    private static void validateFileKeyStore(File keyStore) throws Exception {
        final File file = keyStore;
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        if (!file.isFile()) {
            throw new FileNotFoundException();
        }

        if (file.length() <= 0) {
            throw new FileNotFoundException();
        }

        return;
    }


}
