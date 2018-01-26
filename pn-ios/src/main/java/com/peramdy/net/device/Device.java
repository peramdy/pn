package com.peramdy.net.device;

import java.sql.Timestamp;

/**
 * @author peramdy
 */
public interface Device {

    String getDeviceId();

    void setDeviceId(String deviceId);

    String getToken();

    void setToken(String token);

    void setLastRegister(Timestamp lastRegister);

    Timestamp getLastRegister();

}
