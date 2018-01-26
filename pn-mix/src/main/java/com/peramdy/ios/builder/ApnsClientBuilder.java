package com.peramdy.ios.builder;

import com.peramdy.ios.okhttpClient.ApnsClient;
import com.peramdy.ios.okhttpClient.impl.SyncOkHttpApnsClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author peramdy on 2017/7/18.
 */
public class ApnsClientBuilder {

    private static Logger logger = LoggerFactory.getLogger(ApnsClientBuilder.class);

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
     * common
     */
    private Boolean isProduction = false;    //是否是生产环境
    private OkHttpClient.Builder builder;  //builder
    private ConnectionPool connectionPool; //链接数
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


    /**
     * constructor for jwtToken
     */
    public ApnsClientBuilder(String apnsAuthKey, String teamId, String keyId, String defaultTopic) {
        this.apnsAuthKey = apnsAuthKey;
        this.teamId = teamId;
        this.keyId = keyId;
        this.defaultTopic = defaultTopic;
    }


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
            logger.info("create apnsClient by certificate !");
            return new SyncOkHttpApnsClient(certificate, password, defaultTopic, isProduction, builder);
        } else if (apnsAuthKey != null && teamId != null && keyId != null) {
            logger.info("create apnsClient by token !");
            return new SyncOkHttpApnsClient(apnsAuthKey, teamId, keyId, defaultTopic, isProduction, builder);
        }
        return null;
    }


}
