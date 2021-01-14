package com.example.casbinspringbootdemo.casbin;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Component;

@Component
public class EnforcerWrapper {
    private static Enforcer enforcer;
    private static EnforcerConfigProperties configProperties;

    public EnforcerWrapper(EnforcerConfigProperties configProperties) {
        if (enforcer == null) {
            EnforcerWrapper.configProperties = configProperties;
            enforcer = new Enforcer(configProperties.getModelPath(), configProperties.getPolicyPath());
            enforcer.loadPolicy();
        }
    }

    // TODO: consider adding methods to add and remove policies.

    public Enforcer getEnforcer() {
        return enforcer;
    }
}
