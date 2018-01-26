package com.peramdy.mq.config;

/**
 * @author peramdy on 2018/1/16.
 */
public class ConsumeConfig {

    private String namesrvAddr;
    private String consumerGroupName;
    private String instanceName;
    private boolean isVipChannel;
    private String topic;
    private String tags;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
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


}
