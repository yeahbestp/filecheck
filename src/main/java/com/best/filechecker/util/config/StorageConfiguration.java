package com.best.filechecker.util.config;

import com.best.filechecker.util.config.annotations.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    @Value("${storage.location}")
    private String location;

    @Bean
    @Location
    public String getLocation(){
        return location;
    }
}
