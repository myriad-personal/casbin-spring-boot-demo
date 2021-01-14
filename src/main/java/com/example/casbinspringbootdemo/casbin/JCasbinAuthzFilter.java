package com.example.casbinspringbootdemo.casbin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.jcasbin.main.Enforcer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// NOTE: by default this filter will be registered for every URL in the application

@Component
public class JCasbinAuthzFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCasbinAuthzFilter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Enforcer enforcer;

    public JCasbinAuthzFilter(EnforcerWrapper enforcerWrapper) {
        this.enforcer = enforcerWrapper.getEnforcer();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (authorized(roles(request), request.getRequestURI(), request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(Map.of(
                    "code", 403,
                    "error", "Not Authorized")));
        }
    }

    private boolean authorized(List<String> roleList, String path, String method) {
        for (String role : roleList) {
            if (enforcer.enforce(role, path, method)) return true;
        }
        return false;
    }

    @NotNull
    private List<String> roles(HttpServletRequest request) {
        String user = request.getHeader("user-subject");
        if (!StringUtils.hasText(user)) {
            user = "alice";
        }
        String roles = request.getHeader("user-roles");
        List<String> roleList;
        // The user will be tacked on to the end of the list in both cases below
        if (!StringUtils.hasText(roles)) {
            roleList = List.of("patient", user);
        } else {
            roleList = Arrays.asList(String.format("%s , %s", roles, user).split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
        }
        LOGGER.info("Role list: " + roleList);
        return roleList;
    }
}
