package com.example.casbinspringbootdemo.authz.casbin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import org.casbin.jcasbin.main.Enforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// NOTE: by default this filter will be registered for every URL in the application

@Component
public class JCasbinAuthzFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCasbinAuthzFilter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Enforcer enforcer;
    private final AccessTokenVerifier accessTokenVerifier;

    public JCasbinAuthzFilter(Enforcer enforcer, AccessTokenVerifier accessTokenVerifier) {
        this.enforcer = enforcer;
        this.accessTokenVerifier = accessTokenVerifier;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = user(request);
        if (authorized(user, request.getRequestURI(), request.getMethod())) {
            LOGGER.debug("[Authorized] -> {}, {}, {}", user.userName, request.getRequestURI(), request.getMethod());
            filterChain.doFilter(servletRequest, servletResponse);
            LOGGER.trace("Executed request");
        } else {
            LOGGER.debug("[NOT Authorized] -> {}, {}, {}", user.userName, request.getRequestURI(), request.getMethod());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(Map.of(
                    "code", 403,
                    "error", "Not Authorized")));
        }
    }

    private boolean authorized(User user, String path, String method) {
        for (String role : user.roles) {
            if (enforcer.enforce(role, path, method)) return true;
        }
        return enforcer.enforce(user.userName, path, method);
    }

    /**
     * @param request the request being filtered
     * @return the list of roles used to authorize access.
     */
    protected User user(HttpServletRequest request) throws ServletException {
        Jwt jwt;
        try {
            jwt = accessTokenVerifier.decode(getJwtString(request));
        } catch (JwtVerificationException e) {
            throw new ServletException(e);
        }
        return new User(
                (String) jwt.getClaims().get("appusername"),
                ((List<String>) jwt.getClaims().getOrDefault("groups", List.of())).stream()
                        // Trim off the prefix that comes with the role name from okta.
                        // (inspect your JWT at https://jwt.io to see what I mean)
                        .map(s -> s.trim().substring(s.lastIndexOf(" ") + 1))
                        .collect(Collectors.toList()));
    }

    private String getJwtString(HttpServletRequest request) {
        String jwt = request.getHeader("authorization");
        Assert.hasText(jwt, "Requires Bearer Token");
        return jwt.substring(jwt.lastIndexOf(" ") + 1);
    }

    private static final class User {
        String userName;
        List<String> roles;

        public User(String userName, List<String> roles) {
            this.userName = userName;
            this.roles = roles;
        }
    }
}
