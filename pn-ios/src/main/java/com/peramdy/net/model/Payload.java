package com.peramdy.net.model;

import com.peramdy.net.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
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


    public void addCustomDictionary(final String name, final Object value) throws JSONException {
        put(name, value, payload, false);
    }


    void put(String propertyName, Object propertyValue, JSONObject object, boolean opt) {

        if (opt) {
            object.putOpt(propertyName, propertyValue);
        } else {
            object.put(propertyName, propertyValue);
        }

    }


    @Override
    public String toString() {
        if (this.payload == null) {
            return null;
        }
        return this.payload.toString();
    }


    private byte[] getPayloadAsBytes() throws Exception {
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
}
