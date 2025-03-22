package com.springboot.finnhub;

import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.repository.SymbolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
