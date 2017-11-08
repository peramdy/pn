package com.peramdy.net.server;

import java.io.InputStream;

/**
 * Created by peramdy on 2017/11/7.
 */
public interface AppleServer {


    String getKeyStorePassword();

    String getKeyStoreProtocolType();

    InputStream getKeyStoreStream() throws Exception;


}
