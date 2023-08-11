package com.example.monobank.model;

import com.example.monobank.model.mono.MonoTransactionDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@RequiredArgsConstructor
public class TransactionInfo {
  private String sender;
  private BigDecimal amount;
  private String comment;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime executedAt;


  public static TransactionInfo of(MonoTransactionDetails monoTransactionDetails) {
    TransactionInfo transactionInfo = new TransactionInfo();
    transactionInfo.setSender(monoTransactionDetails.getDescription());
    transactionInfo.setAmount(monoTransactionDetails.getAmount());
    transactionInfo.setComment(monoTransactionDetails.getComment());
    transactionInfo.setExecutedAt(LocalDateTime.ofEpochSecond(monoTransactionDetails.getTime().longValue(), 0, ZoneOffset.UTC));

    return transactionInfo;
  }
}
