package com.example.monobank.model.mono;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonoTransactionDetails {
  private String id;
  private BigDecimal time;
  private String description;
  private BigDecimal mcc;
  private BigDecimal originalMcc;
  private boolean hold;
  private BigDecimal amount;
  private BigDecimal operationAmount;
  private BigDecimal currencyCode;
  private BigDecimal commissionRate;
  private BigDecimal cashbackAmount;
  private BigDecimal balance;
  private String comment;
  private String receiptId;
  private String invoiceId;
  private String counterEdrpou;
  private String counterIban;
  private String counterName;
}