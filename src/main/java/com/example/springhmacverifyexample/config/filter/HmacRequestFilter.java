package com.example.springhmacverifyexample.config.filter;

import com.example.springhmacverifyexample.config.ApplicationProperties;
import com.example.springhmacverifyexample.config.CustomHttpRequestWrapper;
import com.example.springhmacverifyexample.config.HmacVerify;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
public class HmacRequestFilter implements Filter {

    private final ApplicationProperties applicationProperties;

    public HmacRequestFilter(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Hmac Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CustomHttpRequestWrapper wrapper = new CustomHttpRequestWrapper((HttpServletRequest) request);
        HmacVerify.verify(wrapper, applicationProperties.getHmac().getClientSecret());
        chain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }


}
