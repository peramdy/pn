package com.peramdy.net.device;

import java.sql.Timestamp;

/**
 * Created by peramdy on 2017/11/10.
 *
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
