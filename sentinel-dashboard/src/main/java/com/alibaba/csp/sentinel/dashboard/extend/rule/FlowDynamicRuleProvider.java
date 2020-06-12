package com.alibaba.csp.sentinel.dashboard.extend.rule;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
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
public class FlowDynamicRuleProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Resource
    private RuleConfigService ruleConfigService;
    @Resource
    private Converter<String, List<FlowRuleEntity>> converter;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {

        String path = ruleConfigService.getFlowRulePath(appName);
        String rules = ruleConfigService.getRules(path);
        return converter.convert(rules);
    }
}
