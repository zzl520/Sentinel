package com.alibaba.csp.sentinel.dashboard.extend.zookeeper;

import org.apache.curator.framework.AuthInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzl on 2019/5/8.
 * @description
 */
public class ZookeeperUtil {

    public static final String FLOW_RULE_PATH = "/sentinel/rules/%s/flow";
    public static final String AUTHORITY_RULE_PATH = "/sentinel/rules/%s/authority";
    public static final String DEGRADE_RULE_PATH = "/sentinel/rules/%s/degrade";
    public static final String PARAMFLOW_RULE_PATH = "/sentinel/rules/%s/paramflow";
    public static final String SYSTEM_RULE_PATH = "/sentinel/rules/%s/system";


    public static String buildZookeeperPath(String group, String format, String appName) {

        if (group != null && !"".equals(group) && !group.startsWith("/")) {
            group = "/" + group;
        }
        String path = String.format(format, appName);
        return group + path;
    }

    public static List<AuthInfo> getDigestAuthInfo(String username, String password) {
        List<AuthInfo> list = new ArrayList<>();
        if ((username == null || username.length() == 0)
                && (password == null || password.length() == 0)) {
            return list;
        }
        String authority = (username == null ? "" : username) + ":" + (password == null ? "" : password);
        AuthInfo authInfo = new AuthInfo("digest", authority.getBytes());
        list.add(authInfo);
        return list;
    }
}
