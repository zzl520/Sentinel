package com.alibaba.csp.sentinel.dashboard.extend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zzl on 2019/5/7.
 * @description
 */
@ConfigurationProperties(prefix = "sentinel.dashboard")
@Component
public class SentinelDashboardProperties {

    /**
     * 注册中心配置
     */
    private Zookeeper zookeeper;

    public static class Zookeeper {

        /**
         * 服务器地址
         */
        private String address;

        /**
         * 多个团队，环境隔离
         */
        private String group = "";
        /**
         * zk 用户名
         */
        private String username;

        /**
         * zk 密码
         */
        private String password;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    public Zookeeper getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(Zookeeper zookeeper) {
        this.zookeeper = zookeeper;
    }

}
