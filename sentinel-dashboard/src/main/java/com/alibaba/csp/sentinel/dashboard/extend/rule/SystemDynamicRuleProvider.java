package com.alibaba.csp.sentinel.dashboard.extend.rule;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzl on 2019/5/7.
 * @description
 */
@Component
public class SystemDynamicRuleProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {

    @Resource
    private RuleConfigService ruleConfigService;
    @Resource
    private Converter<String, List<SystemRuleEntity>> converter;

    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {

        String path = ruleConfigService.getSystemRulePath(appName);
        String rules = ruleConfigService.getRules(path);
        return converter.convert(rules);
    }
}
