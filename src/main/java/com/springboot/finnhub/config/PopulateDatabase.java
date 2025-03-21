package com.springboot.finnhub.config;

import com.springboot.finnhub.feign.FinnhubClient;
import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.repository.SymbolRepository;
import com.springboot.finnhub.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PopulateDatabase {

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private FinnhubClient finnhubClient;

    @Autowired
    private SymbolService symbolService;

    @Value("${finnhub.api.token}")
    private String finnhubApiToken;

    @Value("${finnhub.exchange}")
    private String finnhubExchange;

    @Bean
    public Object populateDatabaseIfEmpty() {
        if (symbolRepository.count() == 0) {
            List<Symbol> symbols = finnhubClient.populateDatabase(finnhubExchange, finnhubApiToken);
            symbolService.populateDatabase(symbols);
        }
        return new Object();
    }
}