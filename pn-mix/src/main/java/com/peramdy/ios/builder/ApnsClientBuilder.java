package com.peramdy.ios.builder;

import com.peramdy.ios.okhttpClient.ApnsClient;
import com.peramdy.ios.okhttpClient.impl.AsyOkHttpApnsClient;
import com.peramdy.ios.okhttpClient.impl.SyncOkHttpApnsClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by peramdy on 2017/7/18.
 */
public class ApnsClientBuilder {

    /**
     * certificate
     */
    private InputStream certificate; //证书
    private String password;     //密码

    /**
     * token
     */
    private String apnsAuthKey;   //device-token
    private String teamId;        //iss
    private String keyId;         //kid

    /**
     * comon
     */
    private Boolean isProduction = false;    //是否是生产环境
    private OkHttpClient.Builder builder;  //builder
    private ConnectionPool connectionPool; //链接数
    private Boolean isAsynchronous = false;  //是否异步
    private String defaultTopic;


    /**
     * defaultBuilder
     */
    public static OkHttpClient.Builder createDefaultOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(10, 10, TimeUnit.MINUTES));
        return builder;
    }


    /**
     * constructor for certificate
     */

    public ApnsClientBuilder(InputStream certificate, String password, String defaultTopic) {
        this.certificate = certificate;
        this.password = password;
        this.defaultTopic = defaultTopic;
    }

    /*public ApnsClientBuilder addCertificate(InputStream certificate) {
        this.certificate = certificate;
        return this;
    }

    public ApnsClientBuilder addPassword(String password) {
        this.password = password;
        return this;
    }*/


    /**
     * constructor for jwtToken
     */
    public ApnsClientBuilder(String apnsAuthKey, String teamId, String keyId, String defaultTopic) {
        this.apnsAuthKey = apnsAuthKey;
        this.teamId = teamId;
        this.keyId = keyId;
        this.defaultTopic = defaultTopic;
    }
    /*public ApnsClientBuilder addApnsAuthKey(String apnsAuthKey) {
        this.apnsAuthKey = apnsAuthKey;
        return this;
    }

    public ApnsClientBuilder addTeamId(String teamId) {
        this.teamId = teamId;
        return this;
    }

    public ApnsClientBuilder addKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }*/

    /**
     * common
     *
     * @param isProduction
     * @return
     */

    public ApnsClientBuilder isProduction(Boolean isProduction) {
        this.isProduction = isProduction;
        return this;
    }

    public ApnsClientBuilder addConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public ApnsClientBuilder isAsynchronous(boolean isAsynchronous) {
        this.isAsynchronous = isAsynchronous;
        return this;
    }

    public ApnsClientBuilder addBuilder(OkHttpClient.Builder builder) {
        this.builder = builder;
        return this;
    }


    public ApnsClient build() throws Exception {

        //创建defaultBuilder
        if (builder == null) {
            builder = createDefaultOkHttpClientBuilder();
        }

        //链接数
        if (connectionPool != null) {
            builder.connectionPool(connectionPool);
        }

        if (certificate != null) {
            if (isAsynchronous) {//异步请求
                return new AsyOkHttpApnsClient(certificate, password, defaultTopic, isProduction, builder);
            } else {//异步请求
                return new SyncOkHttpApnsClient(certificate, password, defaultTopic, isProduction, builder);
            }
        } else if (apnsAuthKey != null && teamId != null && keyId != null) {
            if (isAsynchronous) {//异步请求
                return new AsyOkHttpApnsClient(apnsAuthKey, teamId, keyId, defaultTopic, isProduction, builder);
            } else {//异步请求
                return new SyncOkHttpApnsClient(apnsAuthKey, teamId, keyId, defaultTopic, isProduction, builder);
            }
        }
        return null;
    }


}
