package com.springboot.finnhub.model;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SymbolSpecification {

    public static Specification<Symbol> filterBy(
            String currency, String description, String displaySymbol,
            String figi, String isin, String mic, String shareClassFIGI,
            String symbol, String symbol2, String type) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (currency != null && !currency.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("currency"), currency));
            }

            if (description != null && !description.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            if (displaySymbol != null && !displaySymbol.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("displaySymbol"), displaySymbol));
            }

            if (figi != null && !figi.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("figi"), figi));
            }

            if (isin != null && !isin.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("isin"), isin));
            }

            if (mic != null && !mic.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("mic"), mic));
            }

            if (shareClassFIGI != null && !shareClassFIGI.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("shareClassFIGI"), shareClassFIGI));
            }

            if (symbol != null && !symbol.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("symbol"), symbol));
            }

            if (symbol2 != null && !symbol2.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("symbol2"), symbol2));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
