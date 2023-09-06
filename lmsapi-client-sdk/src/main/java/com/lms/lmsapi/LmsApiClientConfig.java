package com.lms.lmsapi;


import com.lms.lmsapi.client.LmsApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("lmsapi.client")
@Data
@ComponentScan
public class LmsApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LmsApiClient lmsApiClient() {
        return new LmsApiClient(accessKey, secretKey);
    }

}
