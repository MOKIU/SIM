package com.mokiu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// 这个 MyCorsConfig 类是一个 Spring Boot 的跨域（CORS）配置类，用于解决前端应用（如 Vue/React）访问后端 API 时的跨域限制问题。
// 跨域访问配置
@Configuration
public class MyCorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:6060");
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*"); // 允许哪些请求头过来

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return  new CorsFilter(urlBasedCorsConfigurationSource);
    }
}

// 1. 核心作用
//允许跨域请求：指定哪些外部源（前端地址）可以访问后端 API。
//精细化控制：配置允许的请求方法、头信息、凭据传递等。