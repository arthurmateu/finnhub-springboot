package com.springboot.finnhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Symbol {

    // I could've had a SymbolDTO class, but I decided to skip it to avoid unnecessary complexity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String currency;
    private String description;
    private String displaySymbol;
    private String figi;
    private String isin;
    private String mic;
    private String shareClassFIGI;
    private String symbol;
    private String symbol2;
    private String type;

}