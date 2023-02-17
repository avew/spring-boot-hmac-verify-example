package com.example.springhmacverifyexample.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class ApiResource {

    @PostMapping(value = "/stamp",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postReq(@RequestBody Object o) {
        log.info("RECEIVED API");
        return ResponseEntity.ok().body(o);
    }
}
