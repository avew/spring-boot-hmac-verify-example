package com.example.springhmacverifyexample.repository;

import com.example.springhmacverifyexample.domain.Sn;
import com.example.springhmacverifyexample.domain.SnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnRepository extends JpaRepository<Sn, String> {

    Optional<Sn> findByOrderedIdAndStatus(String orderId, SnStatus status);
}
