package com.springboot.finnhub.feign;

import com.springboot.finnhub.model.Symbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "FinnhubClient", url = "https://finnhub.io/api/v1/")
public interface FinnhubClient {

    @GetMapping("/stock/symbol")
    List<Symbol> populateDatabase(@RequestParam("exchange") String exchange, @RequestParam("token") String token);
}