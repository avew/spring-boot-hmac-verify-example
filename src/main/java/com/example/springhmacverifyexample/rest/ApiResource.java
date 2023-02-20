package com.example.springhmacverifyexample.rest;

import com.example.springhmacverifyexample.domain.Order;
import com.example.springhmacverifyexample.domain.TransactionStatus;
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


    @PostMapping(value = "/stamp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postReq(@RequestBody Order o) {
        o.setStatus(TransactionStatus.ORDER);
        o.setExpiredDate(o.calculateExpire());
        log.info("RECEIVED API {}", o);
        return ResponseEntity.ok().body(o);
    }

}
