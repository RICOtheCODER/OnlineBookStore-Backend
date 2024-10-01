package com.eBookStore.OnlineBookStoreProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//not required
@Configuration
public class WebConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200").allowedOrigins("https://onlinebookstore-gamma.vercel.app")
        // Replace with your frontend domain
                .allowedMethods("GET", "POST",
                "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
