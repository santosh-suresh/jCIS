package com.jifflenow.cis;

import com.jifflenow.cis.service.ExchangeIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JCisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JCisApplication.class, args);
    }
}
