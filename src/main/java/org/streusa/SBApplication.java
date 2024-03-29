package org.streusa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.streusa.services.DatabaseService;

@SpringBootApplication(scanBasePackages = {"org.streusa"})
public class SBApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SBApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SBApplication.class, args);
    }
}
