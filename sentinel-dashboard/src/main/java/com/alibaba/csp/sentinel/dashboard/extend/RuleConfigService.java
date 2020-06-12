package com.alibaba.csp.sentinel.dashboard.extend;

/**
 * @author zzl on 2019/5/7.
 * @description 规则配置服务，用来实现规则的持久化
 */
public interface RuleConfigService {

    /**
     * 获取规则
     *
     * @param path
     * @return
     * @throws Exception
     */
    String getRules(String path) throws Exception;

    /**
     * 更新规则
     *
     * @param path
     * @param rules
     * @throws Exception
     */
    void updateRules(String path, String rules) throws Exception;

    /**
     * 获取黑白名单规则存储路径
     *
     * @param appName
     * @return
     */
    String getAuthorityRulePath(String appName);

    /**
     * 获取降级规则存储路径
     *
     * @param appName
     * @return
     */
    String getDegradeRulePath(String appName);

    /**
     * 获取限流规则存储路径
     *
     * @param appName
     * @return
     */
    String getFlowRulePath(String appName);

    /**
     * 获取热点参数限流规则存储路径
     *
     * @param appName
     * @return
     */
    String getParamFlowRulePath(String appName);

    /**
     * 获取系统保护规则存储路径
     *
     * @param appName
     * @return
     */
    String getSystemRulePath(String appName);

}
