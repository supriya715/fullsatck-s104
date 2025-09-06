package com.klu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CruddemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CruddemoApplication.class, args);
    }

    // This method is required to deploy WAR to external Tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CruddemoApplication.class);
    }
}

