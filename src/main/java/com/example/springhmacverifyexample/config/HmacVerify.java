package com.example.springhmacverifyexample.config;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HmacVerify {

    public static void verify(CustomHttpRequestWrapper request, String secret) {
        String signature = request.getSignature();
        String digest = HmacAlgorithms.HMAC_SHA_256.getName() + ":" + secret + ":" + request.getTimestamp();
        String hmacSHA256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmacHex(digest);
        boolean verify = hmacSHA256.equals(signature);
        if (!verify) {
            throw new RuntimeException("Signature not match");
        }
    }
}
