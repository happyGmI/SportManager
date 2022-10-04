package com.w.sportmanager.config;

//import com.wjw.tjusdnbackendwjw.interceptor.TokenInterceptor;
import com.w.sportmanager.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/api/tokens");  // 用户登录
        excludePath.add("/api/users");     // 注册
        excludePath.add("/api/admin/login");  // 管理员登录
        excludePath.add("/api/callSomething");  // 消费者消息
        excludePath.add("/api/face/**");
        excludePath.add("/static/**");  // 静态资源
        excludePath.add("/templates/**");  // 静态资源

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","HEAD","POST","DELETE","OPTIONS","PUT")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
