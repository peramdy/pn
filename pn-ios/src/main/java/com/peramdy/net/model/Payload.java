package com.peramdy.net.model;

import com.peramdy.net.utils.Constants;
import org.json.JSONObject;

/**
 * Created by peramdy on 2017/11/8.
 */
public class Payload {

    private JSONObject payload;

    private String characterEncoding = Constants.DEFAULT_CHARACTER_ENCODING;

    private int expiry = 1 * 24 * 60 * 60;

    public Payload() {
        super();
        this.payload = new JSONObject();
    }

    public JSONObject getPayload() {
        return this.payload;
    }

    public void addCustomDictionary(final String name, final String value) throws Exception {
        put(name, value, payload, false);
    }

    public void addCustomDictionary(final String name, final int value) throws Exception {
        put(name, value, payload, false);
    }


    protected void put(String propertyName, Object propertyValue, JSONObject object, boolean opt) throws Exception {

        int maximumPayloadSize = getMaximumPayloadSize();
        int estimatedPayloadSize = estimatePayloadSizeAfterAdding(propertyName, propertyValue);
        boolean isExceed = estimatedPayloadSize > maximumPayloadSize;

        if (isExceed) {
            throw new Exception();
        }

        if (opt) {
            object.putOpt(propertyName, propertyValue);
        } else {
            object.put(propertyName, propertyValue);
        }

    }


    private int estimatePayloadSizeAfterAdding(String propertyName, Object propertyValue) throws Exception {

        int currentPayloadSize = getPayloadSize();
        int estimatedSize = currentPayloadSize;
        if (propertyName != null && propertyValue != null) {
            estimatedSize += 6;//,"":""
            estimatedSize += propertyName.getBytes(Constants.DEFAULT_CHARACTER_ENCODING).length;
            int estimatedValueSize = 0;
            if (propertyValue instanceof String || propertyValue instanceof Number) {
                estimatedValueSize = propertyValue.toString().getBytes(Constants.DEFAULT_CHARACTER_ENCODING).length;
            }
            estimatedSize += estimatedValueSize;
        }
        return estimatedSize;
    }


    @Override
    public String toString() {
        if (this.payload == null) {
            return null;
        }
        return this.payload.toString();
    }


    public byte[] getPayloadAsBytes() throws Exception {
        if (toString() == null) {
            return null;
        }
        byte[] bytes = toString().getBytes(characterEncoding);
        return bytes;
    }

    public int getPayloadSize() throws Exception {
        if (getPayloadAsBytes() == null) {
            return 0;
        }
        return getPayloadAsBytes().length;
    }

    public int getMaximumPayloadSize() {
        return Integer.MAX_VALUE;
    }


    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }
}
