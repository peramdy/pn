package com.peramdy.net.device.impl;

import com.peramdy.net.device.Device;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * @author peramdy
 * @date 2017/11/10.
 */
public class BasicDevice implements Device {

    private String deviceId;
    private String token;
    private Timestamp lastRegister;

    public BasicDevice() {
    }

    private BasicDevice(String token, boolean validate) throws Exception {
        this.deviceId = token;
        this.token = token;

        this.lastRegister = new Timestamp(System.currentTimeMillis());
        if (validate) {
            validateTokenFormat(token);
        }
    }

    public static void validateTokenFormat(final String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            throw new Exception();
        }

        if (token.getBytes().length != 64) {
            throw new Exception();
        }
    }


    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setLastRegister(Timestamp lastRegister) {
        this.lastRegister = lastRegister;
    }

    @Override
    public Timestamp getLastRegister() {
        return lastRegister;
    }
}
