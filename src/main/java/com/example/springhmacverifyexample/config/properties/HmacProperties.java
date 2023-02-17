package com.example.springhmacverifyexample.config.properties;


public class HmacProperties {

    private final String clientId = "app-client";
    private final String clientSecret = "app-secret";

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
