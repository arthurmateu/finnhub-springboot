package com.springboot.finnhub.controller;

import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/symbol")
public class SymbolController {

    @Autowired
    private SymbolService service;

    @GetMapping("/all")
    public Page<Symbol> readAllSymbol(Pageable pageable) {
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Symbol> readSymbol(@PathVariable(value = "numberValue") Integer id) {
        return service.read(id);
    }

    @GetMapping("/filter")
    public Page<Symbol> readFilteredSymbol(
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String displaySymbol,
            @RequestParam(required = false) String figi,
            @RequestParam(required = false) String isin,
            @RequestParam(required = false) String mic,
            @RequestParam(required = false) String shareClassFIGI,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) String symbol2,
            @RequestParam(required = false) String type,
            Pageable pageable) {

        return service.readFiltered(currency, description, displaySymbol, figi, isin, mic, shareClassFIGI, symbol, symbol2, type, pageable);
    }

    @PostMapping
    public Symbol createSymbol(@RequestBody Symbol symbol) {
        return service.create(symbol);
    }

    @PutMapping("/{id}")
    public Symbol updateSymbol(@RequestBody Symbol symbol, @PathVariable("numberValue") Integer id) {
        return service.updateSymbol(symbol, id);
    }

    @DeleteMapping("/{id}")
    public String deleteSymbol(@PathVariable("numberValue") Integer id) {
        service.deleteSymbol(id);
        return String.format("Symbol #%s deleted", id.toString());
    }

}