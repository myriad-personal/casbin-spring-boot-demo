package com.example.casbinspringbootdemo.casbin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// NOTE: by default this filter will be registered for every URL in the application

@Component
public class JCasbinAuthzFilter implements Filter {

    private final Enforcer enforcer;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JCasbinAuthzFilter(EnforcerWrapper enforcerWrapper) {
        this.enforcer = enforcerWrapper.getEnforcer();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Before Filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String user = request.getHeader("user-subject");
        if (!StringUtils.hasText(user)) {
            user = "alice";
        }
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (enforcer.enforce(user, path, method)) {
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("After Filter");
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(Map.of("code", 403, "error", "Not Authorized")));
        }
    }

    @Override
    public void destroy() {

    }
}
