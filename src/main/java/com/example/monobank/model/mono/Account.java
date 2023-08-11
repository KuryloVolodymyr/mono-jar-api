package com.example.monobank.model.mono;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {

  private String id;
  private String sendId;
  private BigDecimal currencyCode;
  private String cashbackType;
  private BigDecimal balance;
  private BigDecimal creditLimit;
  private List<String> maskedPan;
  private String type;
  private String iban;
}
