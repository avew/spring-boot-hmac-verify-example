package com.example.springhmacverifyexample.rest;

import com.example.springhmacverifyexample.PeruriService;
import com.example.springhmacverifyexample.domain.Order;
import com.example.springhmacverifyexample.domain.Sn;
import com.example.springhmacverifyexample.domain.SnStatus;
import com.example.springhmacverifyexample.domain.TransactionStatus;
import com.example.springhmacverifyexample.repository.OrderRepository;
import com.example.springhmacverifyexample.repository.SnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ApiOrderResource {


    private final PeruriService peruriService;
    private final OrderRepository orderRepository;
    private final SnRepository snRepository;

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postReq(@RequestBody Order o) {
        log.info("ORDER HAS ORDERED {}", o);

        o.setExpiredDate(o.calculateExpire());

        /* get and set sn from peruri */
        o.add(Sn.builder().sn(peruriService.getSn()).status(SnStatus.UNUSED).ordered(o).build());
        o.setStatus(TransactionStatus.ALREADY_STAMP);
        Order saved = orderRepository.save(o);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postProcess(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "success", defaultValue = "true") boolean success,
            @RequestParam(value = "ref_id") String refId) {
        log.info("PROCESS {}", refId);

        Order order = orderRepository.findByRefId(refId).get();
        if (order.getStatus().equals(TransactionStatus.COMPLETED))
            throw new RuntimeException("Order has been completed");

        /* jika maksimal percobaan sn sudah lebih dari 3 maka transaksi di gagalkan */

        Sn sn = snRepository.findByOrderedIdAndStatus(order.getId(), SnStatus.UNUSED).get();

        boolean stamp = peruriService.stamp(success);

        if (stamp) {
            order.setStatus(TransactionStatus.COMPLETED);
            sn.setStatus(SnStatus.USED);
        } else {

            log.warn("RETRY");
            /* sn sebelumnya di gagalkan */
            sn.setStatus(SnStatus.FAILED);
            order.remove(sn);

            order.add(Sn.builder().sn(peruriService.getSn()).status(SnStatus.UNUSED).ordered(order).build());
            order = orderRepository.save(order);

            //order.setStatus(TransactionStatus.FAILURE);
            //sn.setStatus(SnStatus.FAILED);
        }

        log.debug("ORDER SET TO= {}", order.getStatus());
        log.debug("SN STATUS IS= {}", sn.getStatus());

        order = orderRepository.save(order);


        log.info("ORDER={}", order);
        return ResponseEntity.ok().body(order);
    }

    public void retryStamp(int retry) {

    }

}
