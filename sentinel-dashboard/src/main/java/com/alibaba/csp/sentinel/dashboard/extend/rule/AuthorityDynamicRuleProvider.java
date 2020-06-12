package com.alibaba.csp.sentinel.dashboard.extend.rule;

import com.alibaba.csp.sentinel.dashboard.extend.RuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
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
public class AuthorityDynamicRuleProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

    @Resource
    private RuleConfigService ruleConfigService;
    @Resource
    private Converter<String, List<AuthorityRuleEntity>> converter;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        String path = ruleConfigService.getAuthorityRulePath(appName);
        String rules = ruleConfigService.getRules(path);
        return converter.convert(rules);
    }
}
