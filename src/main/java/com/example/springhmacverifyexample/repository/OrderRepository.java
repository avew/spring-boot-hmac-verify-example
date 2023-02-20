package com.example.springhmacverifyexample.repository;

import com.example.springhmacverifyexample.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByRefId(String refId);
}
