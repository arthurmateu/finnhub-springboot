package com.springboot.finnhub;

import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.model.SymbolSpecification;
import com.springboot.finnhub.repository.SymbolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
@ActiveProfiles("test")
class FinnhubRepositoryTests {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresContainer::getDriverClassName);
    }

    @Autowired
    private SymbolRepository repository;

    private Symbol symbol;

    @BeforeEach
    public void setup() {
        symbol = new Symbol();
        symbol.setSymbol("AAPL");
    }

    @Test
    public void testCreate() {
        Symbol savedSymbol = repository.save(symbol);
        assertThat(savedSymbol.getId()).isNotNull();
    }

    @Test
    public void testReadOne() {
        Symbol savedSymbol = repository.save(symbol);
        Symbol foundSymbol = repository.findById(savedSymbol.getId()).orElse(null);
        assertThat(foundSymbol).isNotNull();
        assertThat(foundSymbol.getSymbol()).isEqualTo("AAPL");
    }

    @Test
    public void testReadAll() {
        repository.save(symbol);

        Symbol anotherSymbol = new Symbol();
        anotherSymbol.setSymbol("GOOG");
        repository.save(anotherSymbol);

        List<Symbol> symbols = repository.findAll();
        assertThat(symbols).hasSize(2);
    }

    @Test
    void testReadFilter() {
        symbol.setCurrency("USD");
        symbol.setDescription("Apple Inc.");
        symbol.setDisplaySymbol("AAPL");
        symbol.setFigi("BBG000B9XRY4");
        symbol.setIsin("US0378331005");
        symbol.setMic("XNAS");
        symbol.setShareClassFIGI("BBG001S5N8V8");
        symbol.setSymbol2("APPLE");
        symbol.setType("Common Stock");

        Symbol symbol2 = new Symbol();
        symbol2.setCurrency("USD");
        symbol2.setDescription("Tesla Inc.");
        symbol2.setDisplaySymbol("TSLA");
        symbol2.setFigi("BBG000N9MNX3");
        symbol2.setIsin("US88160R1014");
        symbol2.setMic("XNAS");
        symbol2.setShareClassFIGI("BBG001S9N6R6");
        symbol2.setSymbol("TSLA");
        symbol2.setSymbol2("TESLA");
        symbol2.setType("Common Stock");

        repository.saveAll(List.of(symbol, symbol2));

        Specification<Symbol> spec = SymbolSpecification.filterBy(null, "Apple", null, null, null, null, null, null, null, null);
        List<Symbol> filteredSymbols = repository.findAll(spec);

        assertThat(filteredSymbols).hasSize(1);
        assertThat(filteredSymbols.get(0).getDescription()).isEqualTo("Apple Inc.");
    }

    @Test
    public void testUpdate() {
        Symbol savedSymbol = repository.save(symbol);
        savedSymbol.setSymbol("MSFT");
        repository.save(savedSymbol);

        Symbol updatedSymbol = repository.findById(savedSymbol.getId()).orElse(null);
        assertThat(updatedSymbol.getSymbol()).isEqualTo("MSFT");
    }

    @Test
    public void testDelete() {
        Symbol savedSymbol = repository.save(symbol);

        repository.deleteById(savedSymbol.getId());
        boolean exists = repository.findById(savedSymbol.getId()).isPresent();
        assertThat(exists).isFalse();
    }
}
