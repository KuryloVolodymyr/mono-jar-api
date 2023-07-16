package com.example.monobank.controller;

import com.example.monobank.model.UserAccountsResponse;
import com.example.monobank.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/info")
@RequiredArgsConstructor
public class UserInfoController {

  private final UserInfoService userInfoService;

  @GetMapping(value = "/donates/{token}")
  public UserAccountsResponse getAllDonates(@PathVariable String token){
    return userInfoService.getAccounts(token);
  }
}
