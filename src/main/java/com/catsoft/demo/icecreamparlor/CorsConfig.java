package com.catsoft.demo.icecreamparlor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@PropertySource("classpath:cors-whitelist.properties")
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedList = new String[0];
        String allowedOrigins = this.env.getProperty("allowed.origins");
        if (allowedOrigins == null)
            System.err.println("There are no CORS allowed origins configured");
        else
            allowedList = allowedOrigins.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedList)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
