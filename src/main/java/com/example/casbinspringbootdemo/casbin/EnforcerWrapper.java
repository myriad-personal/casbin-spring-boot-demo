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
            enforcer = new Enforcer(
                    "/home/thart/spikes/casbin/casbin-spring-boot-demo/src/main/resources/casbin/model.conf",
                    "/home/thart/spikes/casbin/casbin-spring-boot-demo/src/main/resources/casbin/policy.csv");
            enforcer.loadPolicy();
        }
    }

    // TODO: add methods to add and remove policies

    public Enforcer getEnforcer() {
        return enforcer;
    }
}
