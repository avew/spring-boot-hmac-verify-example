package com.example.springhmacverifyexample;

import com.example.springhmacverifyexample.config.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PeruriService {

    public String getSn() {
        return Util.randomString();
    }

    public boolean stamp(boolean success) {
        return success;
    }
}
