package com.alibaba.csp.sentinel.dashboard.extend.rule;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
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
public class DegradeDynamicRulePublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {

    @Resource
    private RuleConfigService ruleConfigService;
    @Resource
    private Converter<List<DegradeRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {

        String path = ruleConfigService.getDegradeRulePath(app);
        String rule = converter.convert(rules);
        ruleConfigService.updateRules(path, rule);
    }
}
