package com.example.casbinspringbootdemo.authz;

import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.JwtVerifiers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AccessTokenVerifierConfiguration {

    @Value("${security.jwt.issuer}")
    String jwtIssuer;
    AccessTokenVerifier accessTokenVerifier;

    @Bean
    public synchronized AccessTokenVerifier accessTokenVerifier() {
        if (accessTokenVerifier == null) {
            accessTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
                    .setIssuer(jwtIssuer)
                    .setAudience("https://applications.myriad.com/blox-server")
                    .setConnectionTimeout(Duration.ofSeconds(1))// defaults to 1s
                    .setRetryMaxAttempts(2)                     // defaults to 2
                    .setRetryMaxElapsed(Duration.ofSeconds(10)) // defaults to 10s
                    .build();
        }
        return accessTokenVerifier;
    }
}
