package com.peramdy.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author peramdy
 * @date 2018/1/8.
 */
public class SerializationUtils {


    /**
     * cache
     */
    private static Map<Class<?>, Schema<?>> cacheSchema = new ConcurrentHashMap<>();

    /**
     * newStance
     */
    private static Objenesis objenesis = new ObjenesisStd(true);

    /**
     * constructor
     */
    public SerializationUtils() {
    }

    /**
     * getSchema
     *
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cacheSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(cls);
            cacheSchema.put(cls, schema);
        }
        return schema;
    }

    /**
     * serializer
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String serializer(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<T> schema = getSchema(cls);
        byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, linkedBuffer);
        String str = null;
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * deserializer
     *
     * @param strData
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String strData, Class<T> cls) {
        try {
            T message = (T) objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            byte[] data = strData.getBytes("UTF-8");
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
