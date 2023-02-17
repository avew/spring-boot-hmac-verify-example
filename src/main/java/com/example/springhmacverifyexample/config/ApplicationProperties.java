package com.example.springhmacverifyexample.config;

import com.example.springhmacverifyexample.config.properties.HmacProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final HmacProperties hmac = new HmacProperties();

    public HmacProperties getHmac() {
        return hmac;
    }
}
