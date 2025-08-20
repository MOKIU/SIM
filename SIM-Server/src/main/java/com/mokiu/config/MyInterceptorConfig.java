package com.mokiu.config;

import com.mokiu.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/admin/login",
                        "/admin/info",
                        "/admin/logout",
                        "/error",
                        "/swagger-resources/**",
                        "/v3/**",
                        "/swagger-ui/**"
                );
    }
}

// 1. 核心作用
//全局 JWT 验证：通过 JwtValidateInterceptor 拦截请求，验证 Token 有效性。
//路径过滤：灵活配置需要拦截和放行的 API 路径。
//与 Swagger 集成：排除 Swagger 相关路径，确保文档可访问。