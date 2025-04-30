package br.com.miqueias.recruta_auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    private Long Expiration;
    private Long RefreshExpiration;

    public Long getExpiration() {
        return Expiration;
    }

    public void setExpiration(Long expiration) {
        Expiration= expiration;
    }

    public Long getRefreshExpiration() {
        return RefreshExpiration;
    }

    public void setRefreshExpiration(Long refreshExpiration) {
        RefreshExpiration = refreshExpiration;
    }
}
