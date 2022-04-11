package com.example.onlineoj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("拦截器启动");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/admin.html");

    }
}
