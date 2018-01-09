package com.peramdy.redis;

/**
 * @author peramdy
 * @date 2018/1/8.
 */
public class RedisConfig {

    /**
     * redis url
     */
    private String redisUrl;

    /**
     * redis port
     */
    private int port;

    /**
     * redis password
     */
    private String password;

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * constructor
     *
     * @param redisUrl
     * @param port
     */
    public RedisConfig(String redisUrl, int port) {
        this.redisUrl = redisUrl;
        this.port = port;
    }

    /**
     * constructor
     *
     * @param redisUrl
     * @param port
     * @param password
     */
    public RedisConfig(String redisUrl, int port, String password) {
        this.redisUrl = redisUrl;
        this.port = port;
        this.password = password;
    }
}
