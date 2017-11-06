package com.peramdy.ios.okhttpClient.impl;

import com.peramdy.ios.builder.ApnsRequestBuilder;
import com.peramdy.ios.comm.HttpCode;
import com.peramdy.ios.comm.HttpConstant;
import com.peramdy.ios.model.Notification;
import com.peramdy.ios.okhttpClient.ApnsClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * Created by peramdy on 2017/7/18.
 * 同步
 */
public class SyncOkHttpApnsClient implements ApnsClient {


    /**
     * httpClient
     */
    protected final OkHttpClient client;

    /**
     * apns Provider Authentication Tokens
     */
    private final String apnsAuthKey;

    /**
     * The issuer (iss) registered claim key, whose value is your 10-character Team ID, obtained from your developer account
     */
    private final String teamId;
    /**
     * A 10-character key identifier (kid) key, obtained from your developer account
     */
    private final String keyId;

    /**
     * APNs Connections
     */
    private final String gateway;

    private final String defaultTopic;

    /**
     * certification 方式
     *
     * @param certificate  证书
     * @param password     证书密码
     * @param isProduction 是否是生产环境
     * @param builder      httpClientBuilder
     * @throws Exception
     */
    public SyncOkHttpApnsClient(InputStream certificate, String password, String defaultTopic, Boolean isProduction, OkHttpClient.Builder builder)
            throws Exception {

        apnsAuthKey = teamId = keyId = null;

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certificate, password.toCharArray());

//            final X509Certificate cert = (X509Certificate) ks.getCertificate(ks.aliases().nextElement());
//            CertificateUtils.validateCertificate(production, cert);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        KeyManager[] keyManagers = kmf.getKeyManagers();

        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);

        TrustManager[] trustManagers = tmf.getTrustManagers();

        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }

        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

        SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(keyManagers, tmf.getTrustManagers(), null);

        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        builder.sslSocketFactory(sslSocketFactory, trustManager);

        this.defaultTopic = defaultTopic;

        client = builder.build();

        gateway = isProduction ? HttpConstant.ENDPOINT_PRODUCTION : HttpConstant.ENDPOINT_SANDBOX;
    }

    /**
     * jwtToken方式
     *
     * @param apnsAuthKey  生产p8文件的key(开发者账号提供)
     * @param teamId       (开发者账号提供)
     * @param keyId        (开发者账号提供)
     * @param isProduction 是否是生产环境
     * @param builder      httpClientBuilder
     */
    public SyncOkHttpApnsClient(String apnsAuthKey, String teamId, String keyId, String defaultTopic, Boolean isProduction, OkHttpClient.Builder builder) {
        this.apnsAuthKey = apnsAuthKey;
        this.teamId = teamId;
        this.keyId = keyId;
        this.defaultTopic = defaultTopic;
        client = builder.build();
        gateway = isProduction ? HttpConstant.ENDPOINT_PRODUCTION : HttpConstant.ENDPOINT_SANDBOX;

    }


    public String getApnsAuthKey() {
        return apnsAuthKey;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getGateway() {
        return gateway;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    /**
     * 推送消息
     *
     * @param notification
     * @return
     */
    public com.peramdy.ios.comm.Response push(final Notification notification) {
        Response response = null;
        com.peramdy.ios.comm.Response myResponse = null;
        ApnsRequestBuilder apnsRequestBuilder = new ApnsRequestBuilder(apnsAuthKey, teamId, keyId, gateway, defaultTopic);
        Request request = apnsRequestBuilder.build(notification);
        try {

            response = client.newCall(request).execute();
            myResponse = parseResponse(response);
            parseResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return new com.peramdy.ios.comm.Response(-1, null, e, HttpCode.getHttpCode(-1));
        }
        return myResponse;
    }


    protected com.peramdy.ios.comm.Response parseResponse(Response response) {
        String contentBody = null;
        int statusCode = 0;
        HttpCode status = null;
        try {
            statusCode = response.code();
            status = HttpCode.getHttpCode(statusCode);
            if (statusCode != 200)
                contentBody = response.body() != null ? response.body().toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return new com.peramdy.ios.comm.Response(-1, null, e, HttpCode.getHttpCode(-1));
        }
        return new com.peramdy.ios.comm.Response(statusCode, contentBody, null, status);
    }

    public OkHttpClient getClient() {
        return client;
    }


}
