package com.example.orderserver;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${redisson.address}")
    private String addressUrl;
    @Value("${redisson.password}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setTimeout(10000)
                .setAddress(addressUrl).setPassword(password)
                .setPingConnectionInterval(60000)
                .setRetryInterval(5000)
                .setConnectTimeout(10000);
        return Redisson.create(config);
    }






}