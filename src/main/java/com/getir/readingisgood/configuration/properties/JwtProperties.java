package com.getir.readingisgood.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class JwtProperties {
    private String jwtSecret;
    private Long jwtExpirationInMs;
    private Long jwtRefreshExpirationInMs;
}
