package com.springboot.finnhub;

import com.springboot.finnhub.model.Symbol;
import com.springboot.finnhub.repository.SymbolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@Transactional
class FinnhubRepositoryTests {

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
        // NOTE: this seems to fail because it's not actually creating everything on the H2 db. Will check later
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
