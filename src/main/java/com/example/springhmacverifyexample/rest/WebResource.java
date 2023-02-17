package com.example.springhmacverifyexample.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/web")
@Slf4j
public class WebResource {

    @PostMapping("/stamp")
    public ResponseEntity<Object> postReq(@RequestBody Object o) {
        log.info("REQ BODY FOR WEB= {}", o);
        return ResponseEntity.ok().build();
    }
}
