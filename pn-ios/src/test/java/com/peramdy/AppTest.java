package com.peramdy;


import com.peramdy.net.okhttp.OkHttpClient;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AppTest extends TestCase {


    public void testOne() {


        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            File file = new File("E:\\srca12306\\srca.cer");
            InputStream in = new FileInputStream(file);
            okHttpClient.run(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
