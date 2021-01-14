package com.example.casbinspringbootdemo.casbin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "org.jcasbin")
public class EnforcerConfigProperties {

    private String jdbcUrl;

    private String jdbcDriverClassName;

    private String jdbcUsername;

    private String jdbcPassword;

    private String modelPath;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    @Override
    public String toString() {
        return "EnforcerConfigProperties [jdbcUrl=" + jdbcUrl + ", jdbcDriverClassName=" + jdbcDriverClassName + ", jdbcUsername="
                + jdbcUsername + ", jdbcPassword=" + jdbcPassword + ", modelPath=" + modelPath + "]";
    }

}