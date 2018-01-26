package com.peramdy.mq.config;

/**
 * @author peramdy on 2018/1/16.
 */
public class ProduceConfig {

    private String namesrvAddr;
    private String producerGroupName;
    private String instanceName;
    private boolean isVipChannel;
    private String topic;
    private String tags;
    private String keys;
    private byte[] body;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public boolean isVipChannel() {
        return isVipChannel;
    }

    public void setVipChannel(boolean vipChannel) {
        isVipChannel = vipChannel;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
