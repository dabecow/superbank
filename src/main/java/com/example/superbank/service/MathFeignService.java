package com.example.superbank.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "math", url = "localhost:8081/")
public interface MathFeignService {

    @GetMapping("/commission")
    BigDecimal getCommission(@RequestParam BigDecimal moneySum);
}
