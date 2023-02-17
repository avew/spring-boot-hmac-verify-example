package com.example.springhmacverifyexample.config;

import lombok.SneakyThrows;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static com.example.springhmacverifyexample.config.HmacConstant.*;

public class CustomHttpRequestWrapper extends HttpServletRequestWrapper {

    private final String requestBody;
    private final String signature;
    private final String clientId;
    private final String timestamp;

    @SneakyThrows
    public CustomHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator())).replaceAll("\\s+", "");
        signature = request.getHeader(SIGNATURE);
        clientId = request.getHeader(CLIENT_ID);
        timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_TS));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    @SneakyThrows
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getSignature() {
        return signature;
    }

    public String getClientId() {
        return clientId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
