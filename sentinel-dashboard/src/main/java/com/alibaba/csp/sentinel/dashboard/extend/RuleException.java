package com.alibaba.csp.sentinel.dashboard.extend;

/**
 * @author zzl on 2019/5/7.
 * @description
 */
public class RuleException extends RuntimeException {

    public RuleException(Exception e) {
        super(e);
    }
}
