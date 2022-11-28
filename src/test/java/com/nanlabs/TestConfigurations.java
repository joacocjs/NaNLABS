package com.nanlabs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@TestPropertySource(locations="classpath:application-test.yml")
public class TestConfigurations {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
