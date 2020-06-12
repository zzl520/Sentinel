package com.alibaba.csp.sentinel.dashboard.extend.rule;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzl on 2019/5/7.
 * @description
 */
@Component
public class SystemDynamicRulePublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {

    @Resource
    private RuleConfigService ruleConfigService;
    @Resource
    private Converter<List<SystemRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<SystemRuleEntity> rules) throws Exception {

        String path = ruleConfigService.getSystemRulePath(app);
        String rule = converter.convert(rules);
        ruleConfigService.updateRules(path, rule);
    }
}
