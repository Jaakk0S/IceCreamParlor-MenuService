package com.catsoft.demo.icecreamparlor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
    Configures the service for CORS.
    If dev.mode == true, all origins are allowed. Otherwise, only those listed in cors-whitelist.properties
 */
@Configuration
@PropertySource("classpath:cors-whitelist.properties")
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Value("${dev.mode}")
    public boolean devMode;

    private CorsRegistration setOrigins(CorsRegistration registration) {
        if (this.devMode)
            return registration.allowedOriginPatterns("*");
        String allowedOrigins = this.env.getProperty("allowed.origins");
        if (allowedOrigins == null) {
            System.err.println("Service not in dev mode and there are no CORS allowed origins configured");
            return registration;
        }
        return registration.allowedOriginPatterns(allowedOrigins.split(","));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        this.setOrigins(registry.addMapping("/**"))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .maxAge(3600);
    }

}
