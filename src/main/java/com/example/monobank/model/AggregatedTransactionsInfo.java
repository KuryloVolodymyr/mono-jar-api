package com.example.monobank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregatedTransactionsInfo implements Comparable<AggregatedTransactionsInfo> {
  private String sender;
  private BigDecimal totalAmount = BigDecimal.ZERO;
  private List<TransactionInfo> detailedTransactionsInfo = new ArrayList<>();

  @Override
  public int compareTo(AggregatedTransactionsInfo o) {
    return o.totalAmount.compareTo(this.totalAmount);
  }

  public AggregatedTransactionsInfo addTransaction(TransactionInfo transactionInfo) {
    transactionInfo.setAmount(transactionInfo.getAmount().divide(BigDecimal.valueOf(100)));

    totalAmount = totalAmount.add(transactionInfo.getAmount());
    this.detailedTransactionsInfo.add(transactionInfo);
    return this;
  }
}
