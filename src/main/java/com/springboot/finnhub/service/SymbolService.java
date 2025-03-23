package com.springboot.finnhub.service;

import com.springboot.finnhub.feign.FinnhubClient;
import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.model.SymbolSpecification;
import com.springboot.finnhub.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class SymbolService {
    @Autowired
    private SymbolRepository repository;

    @Autowired
    private FinnhubClient client;

    // Populate if db is empty
    public void populateDatabase(List<Symbol> finnhubSymbols) {
        repository.saveAll(finnhubSymbols);
    }

    // Create
    public Symbol create(Symbol symbol) {
        return repository.save(symbol);
    }

    // Read
    public Page<Symbol> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Symbol> read(Integer id) {
        return repository.findById(id);
    }

    public Page<Symbol> readFiltered(
            String currency, String description, String displaySymbol,
            String figi, String isin, String mic, String shareClassFIGI,
            String symbol, String symbol2, String type, Pageable pageable) {

        Specification<Symbol> spec = SymbolSpecification.filterBy(
                currency, description, displaySymbol, figi, isin, mic,
                shareClassFIGI, symbol, symbol2, type);

        return repository.findAll(spec, pageable);
    }

    // Update
    public Symbol updateSymbol(Symbol symbol, Integer id) {

        return repository.findById(id)
                .map(s -> {
                    s.setCurrency(symbol.getCurrency());
                    s.setDescription(symbol.getDescription());
                    s.setDisplaySymbol(symbol.getDisplaySymbol());
                    s.setFigi(symbol.getFigi());
                    s.setIsin(symbol.getIsin());
                    s.setMic(symbol.getMic());
                    s.setShareClassFIGI(symbol.getShareClassFIGI());
                    s.setSymbol(symbol.getSymbol());
                    s.setSymbol2(symbol.getSymbol2());
                    s.setType(symbol.getType());
                    return repository.save(s);
                })
                .orElseGet(() -> repository.save(symbol));
    }

    // Delete
    public void deleteSymbol(Integer id) {
        repository.deleteById(id);
    }

}
