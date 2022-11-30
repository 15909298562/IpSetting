package com.imohuing.datasource.module;

import java.time.LocalDateTime;

/**
 * @Author Yangws
 * @Date 2022-11-16 23:56
 * @Description
 * @Version 1.0.0
 **/
public class SettingHistory {

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 网络适配器
     */
    private String adapter;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 子网掩码
     */
    private String mask;
    /**
     * 网关地址
     */
    private String gateway;
    /**
     * DNS服务器地址
     */
    private String dns;
    /**
     * 最后使用时间
     */
    private LocalDateTime lastUsedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public LocalDateTime getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(LocalDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }
}
