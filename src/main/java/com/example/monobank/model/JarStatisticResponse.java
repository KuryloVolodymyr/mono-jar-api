package com.example.monobank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class JarStatisticResponse {

  private BigDecimal totalAmount;
  private List<TransactionDetails> transactions;

}
