package com.example.monobank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Jar {
  private String id;
  private String sendId;
  private String title;
  private String description;
  private BigDecimal currencyCode;
  private BigDecimal balance;
  private BigDecimal goal;
}
