package com.example.springhmacverifyexample.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    public static String getCurrentUserIp(HttpServletRequest httpServletRequest) {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");

        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        }

        return xfHeader.split(",")[0];
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();
        String url = req.getRequestURL().toString();
        if (req.getQueryString() != null) {
            url += "?" + req.getQueryString();
        }
        String ip = getCurrentUserIp(req);
        log.info("REQ: {}, URL: {}, IP: {}", method, url, ip);

        chain.doFilter(request, response);

        log.info("RES: {}, URL: {}, IP: {}, STATUS: {}", method, url, ip, res.getStatus());

    }
}