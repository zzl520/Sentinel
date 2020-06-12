package com.alibaba.csp.sentinel.dashboard.extend.zookeeper;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.extend.config.SentinelDashboardProperties;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zzl on 2019/5/7.
 * @description
 */
@Component
public class ZookeeperRuleConfigService implements InitializingBean, RuleConfigService {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperRuleConfigService.class);

    @Resource
    private SentinelDashboardProperties sentinelDashboardProperties;

    private CuratorFramework zkClient = null;

    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;

    @Override
    public void afterPropertiesSet() {

        //启动client
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().
                connectString(sentinelDashboardProperties.getZookeeper().getAddress()).
                retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        List<AuthInfo> digestAuthInfo = ZookeeperUtil.getDigestAuthInfo(sentinelDashboardProperties.getZookeeper().getUsername(), sentinelDashboardProperties.getZookeeper().getPassword());
        if (digestAuthInfo.size() > 0) {
            //配置权限
            builder.authorization(digestAuthInfo);
        }
        zkClient = builder.build();
        zkClient.start();
    }

    @Override
    public String getRules(String path) throws Exception {
        byte[] bytes = new byte[0];
        try {
            bytes = zkClient.getData().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            logger.warn("no node", e);
        }
        if (null == bytes || bytes.length == 0) {
            logger.info("getRules success,path={},rules is null", path);
            return "";
        }

        String rules = new String(bytes, StandardCharsets.UTF_8);
        logger.info("getRules success,path={},rules={}", path, rules);
        return rules;
    }


    @Override
    public void updateRules(String path, String rules) throws Exception {
        if (rules == null) {
            return;
        }
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, rules.getBytes(StandardCharsets.UTF_8));

        logger.info("updateRules success,path={},rules={}", path, rules);
    }

    @Override
    public String getAuthorityRulePath(String appName) {

        return ZookeeperUtil.buildZookeeperPath(sentinelDashboardProperties.getZookeeper().getGroup(), ZookeeperUtil.AUTHORITY_RULE_PATH, appName);
    }

    @Override
    public String getDegradeRulePath(String appName) {
        return ZookeeperUtil.buildZookeeperPath(sentinelDashboardProperties.getZookeeper().getGroup(), ZookeeperUtil.DEGRADE_RULE_PATH, appName);
    }

    @Override
    public String getFlowRulePath(String appName) {
        return ZookeeperUtil.buildZookeeperPath(sentinelDashboardProperties.getZookeeper().getGroup(), ZookeeperUtil.FLOW_RULE_PATH, appName);
    }

    @Override
    public String getParamFlowRulePath(String appName) {
        return ZookeeperUtil.buildZookeeperPath(sentinelDashboardProperties.getZookeeper().getGroup(), ZookeeperUtil.PARAMFLOW_RULE_PATH, appName);
    }

    @Override
    public String getSystemRulePath(String appName) {
        return ZookeeperUtil.buildZookeeperPath(sentinelDashboardProperties.getZookeeper().getGroup(), ZookeeperUtil.SYSTEM_RULE_PATH, appName);
    }
}
