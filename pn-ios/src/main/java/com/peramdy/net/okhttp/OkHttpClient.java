package com.peramdy.net.okhttp;

import com.peramdy.net.exception.CustomException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

/**
 * @author peramdy on 2018/1/12.
 */
public class OkHttpClient {


    private okhttp3.OkHttpClient httpClient;

    public okhttp3.OkHttpClient createHttpClient(InputStream... certificates) {

        TrustManagerFactory trustManagerFactory = getTrustManagerFactory(certificates);

        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(trustManagerFactory);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new CustomException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient
                .Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0])
                .build();

        return client;
    }


    private TrustManagerFactory getTrustManagerFactory(InputStream... certificates) {
        //用我们的证书创建一个keystore
        TrustManagerFactory trustManagerFactory = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = null;
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, "password".toCharArray());
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = "server" + Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                certificate.close();
            }
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trustManagerFactory;
    }


    private SSLSocketFactory getSSLSocketFactory(TrustManagerFactory trustManagerFactory) {

        //创建一个trustmanager，只信任我们创建的keystore
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), SecureRandom.getInstanceStrong());
//            sslContext.init(null, new TrustManager[]{new TrustManagerServer()}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }


    public void run(InputStream... certificates) {
        Request request = new Request.Builder().post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {

            }
        }).url("https://kyfw.12306.cn/otn/").build();

        okhttp3.OkHttpClient client = createHttpClient(certificates);

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected code " + response);
//                }
//
//                Headers responseHeaders = response.headers();
//                for (int i = 0; i < responseHeaders.size(); i++) {
//                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
//
//                System.out.println(response.body().string());
//            }
//        });
    }


}
