package com.alibaba.csp.sentinel.dashboard.extend;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zzl on 2019/5/7.
 * 继承{@link SentinelApiClient}是为了不改动controller，减少对源码的改动
 */
@Component
@Primary
public class DynamicRuleClient extends SentinelApiClient {

    private final Logger logger = LoggerFactory.getLogger(DynamicRuleClient.class);


    @Resource
    @Qualifier("authorityDynamicRuleProvider")
    private DynamicRuleProvider<List<AuthorityRuleEntity>> authorityDynamicRuleProvider;
    @Resource
    @Qualifier("authorityDynamicRulePublisher")
    private DynamicRulePublisher<List<AuthorityRuleEntity>> authorityDynamicRulePublisher;

    @Resource
    @Qualifier("degradeDynamicRuleProvider")
    private DynamicRuleProvider<List<DegradeRuleEntity>> degradeDynamicRuleProvider;
    @Resource
    @Qualifier("degradeDynamicRulePublisher")
    private DynamicRulePublisher<List<DegradeRuleEntity>> degradeDynamicRulePublisher;

    @Resource
    @Qualifier("flowDynamicRuleProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> flowDynamicRuleProvider;
    @Resource
    @Qualifier("flowDynamicRulePublisher")
    private DynamicRulePublisher<List<FlowRuleEntity>> flowDynamicRulePublisher;

    @Resource
    @Qualifier("paramFlowDynamicRuleProvider")
    private DynamicRuleProvider<List<ParamFlowRuleEntity>> paramFlowDynamicRuleProvider;
    @Resource
    @Qualifier("paramFlowDynamicRulePublisher")
    private DynamicRulePublisher<List<ParamFlowRuleEntity>> paramFlowDynamicRulePublisher;

    @Resource
    @Qualifier("systemDynamicRuleProvider")
    private DynamicRuleProvider<List<SystemRuleEntity>> systemDynamicRuleProvider;
    @Resource
    @Qualifier("systemDynamicRulePublisher")
    private DynamicRulePublisher<List<SystemRuleEntity>> systemDynamicRulePublisher;


    @Override
    public List<FlowRuleEntity> fetchFlowRuleOfMachine(String app, String ip, int port) {

        try {
            return flowDynamicRuleProvider.getRules(app);
        } catch (Exception e) {
            throw new RuleException(e);
        }
    }

    @Override
    public List<DegradeRuleEntity> fetchDegradeRuleOfMachine(String app, String ip, int port) {

        try {
            return degradeDynamicRuleProvider.getRules(app);
        } catch (Exception e) {
            throw new RuleException(e);
        }
    }

    @Override
    public List<SystemRuleEntity> fetchSystemRuleOfMachine(String app, String ip, int port) {
        try {
            return systemDynamicRuleProvider.getRules(app);
        } catch (Exception e) {
            throw new RuleException(e);
        }
    }

    @Override
    public CompletableFuture<List<ParamFlowRuleEntity>> fetchParamFlowRulesOfMachine(String app, String ip, int port) {

        CompletableFuture<List<ParamFlowRuleEntity>> future = new CompletableFuture<>();
        try {
            List<ParamFlowRuleEntity> rules = paramFlowDynamicRuleProvider.getRules(app);
            future.complete(rules);
        } catch (Exception e) {

            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public List<AuthorityRuleEntity> fetchAuthorityRulesOfMachine(String app, String ip, int port) {
        try {
            return authorityDynamicRuleProvider.getRules(app);
        } catch (Exception e) {
            throw new RuleException(e);
        }
    }

    @Override
    public boolean setFlowRuleOfMachine(String app, String ip, int port, List<FlowRuleEntity> rules) {

        try {
            flowDynamicRulePublisher.publish(app, rules);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    @Override
    public boolean setDegradeRuleOfMachine(String app, String ip, int port, List<DegradeRuleEntity> rules) {
        try {
            degradeDynamicRulePublisher.publish(app, rules);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    @Override
    public boolean setSystemRuleOfMachine(String app, String ip, int port, List<SystemRuleEntity> rules) {
        try {
            systemDynamicRulePublisher.publish(app, rules);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    @Override
    public boolean setAuthorityRuleOfMachine(String app, String ip, int port, List<AuthorityRuleEntity> rules) {
        try {
            authorityDynamicRulePublisher.publish(app, rules);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    @Override
    public CompletableFuture<Void> setParamFlowRuleOfMachine(String app, String ip, int port, List<ParamFlowRuleEntity> rules) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            paramFlowDynamicRulePublisher.publish(app, rules);
            future.complete(null);
        } catch (Exception e) {
            logger.error("", e);
            future.completeExceptionally(e);
        }
        return future;
    }
}
