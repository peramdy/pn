package com.peramdy.net.model;

import com.peramdy.net.device.Device;

/**
 * @author peramdy
 * @date 2017/11/10.
 */
public class PushedNotification {

    private Payload payload;
    private Device device;
    private Integer identifier;
    private Long expiry;


    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }


    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }
}
