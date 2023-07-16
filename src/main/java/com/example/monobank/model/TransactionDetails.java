package com.example.monobank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetails implements Comparable<TransactionDetails> {
  private String sender;
  private BigDecimal amount;
  private String comment;

  @Override
  public int compareTo(TransactionDetails o) {
    return o.amount.compareTo(this.amount);
  }
}
