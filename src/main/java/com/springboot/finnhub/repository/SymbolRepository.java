package com.springboot.finnhub.repository;

import com.springboot.finnhub.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Integer>, JpaSpecificationExecutor<Symbol> {
}