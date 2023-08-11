package com.example.monobank.model;

import com.example.monobank.model.mono.Account;
import com.example.monobank.model.mono.Jar;
import lombok.Data;

import java.util.List;

@Data
public class UserAccountsResponse {

  private String clientId;
  private String name;
  private String webHookUrl;
  private String permissions;
  private List<Account> accounts;
  private List<Jar> jars;
}

