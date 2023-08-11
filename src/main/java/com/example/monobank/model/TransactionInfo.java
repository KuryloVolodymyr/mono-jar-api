package com.example.monobank.model;

import com.example.monobank.model.mono.MonoTransactionDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class TransactionInfo {
  private String sender;
  private BigDecimal amount;
  private String comment;
  private BigDecimal executedAt;


  public static TransactionInfo of(MonoTransactionDetails monoTransactionDetails) {
    TransactionInfo transactionInfo = new TransactionInfo();
    transactionInfo.setSender(monoTransactionDetails.getDescription());
    transactionInfo.setAmount(monoTransactionDetails.getAmount());
    transactionInfo.setComment(monoTransactionDetails.getComment());
    transactionInfo.setExecutedAt(monoTransactionDetails.getTime());

    return transactionInfo;
  }
}
