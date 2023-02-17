package com.example.springhmacverifyexample.config.filter;

import com.example.springhmacverifyexample.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppFilterConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public FilterRegistrationBean<HmacRequestFilter> hmacFilter() {
        FilterRegistrationBean<HmacRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HmacRequestFilter(applicationProperties));
        registrationBean.addUrlPatterns("/api/v1/stamp");
        registrationBean.setOrder(2);
        return registrationBean;

    }
}
