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

    // uncomment this and comment the @Component in the filter class definition to register only for a url pattern
    @Bean
    public FilterRegistrationBean<HmacFilter> hmacFilter() {
        FilterRegistrationBean<HmacFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HmacFilter(applicationProperties));
        registrationBean.addUrlPatterns("/api/v1/stamp");
        registrationBean.setOrder(1);
        return registrationBean;

    }
}
