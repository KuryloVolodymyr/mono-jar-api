package com.example.monobank.service;

import com.example.monobank.client.MonoBankClient;
import com.example.monobank.model.UserAccountsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

  private final MonoBankClient monoBankClient;

  public UserAccountsResponse getAccounts(String token) {
    return monoBankClient.getAccountInfo(token);
  }
}
