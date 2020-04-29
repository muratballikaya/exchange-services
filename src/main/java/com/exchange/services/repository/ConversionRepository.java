package com.exchange.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.services.model.Conversion;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
