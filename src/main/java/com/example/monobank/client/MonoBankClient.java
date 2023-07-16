package com.example.monobank.client;

import com.example.monobank.model.JarTransaction;
import com.example.monobank.model.UserAccountsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MonoBankClient {

  @Value("${monobank.api.user-token}")
  private String token;

  @Value("${monobank.api.base-url}")
  private String baseUrl;

  @Value("${monobank.api.statements-url}")
  private String statementsUrl;

  @Value("${monobank.api.client-info-url}")
  private String clientInfoUrl;

  public static final String MONO_AUTH_HEADER_NAME = "X-Token";

  private final RestTemplate restTemplate;

  public List<JarTransaction> getJarTransactions(String accountId, String startDate) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(MONO_AUTH_HEADER_NAME, token);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);

    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("accountId", accountId);
    uriParams.put("startTime", startDate);

    ResponseEntity<JarTransaction[]> exchange = restTemplate.exchange(baseUrl + statementsUrl, HttpMethod.GET, entity, JarTransaction[].class, uriParams);
    return List.of(exchange.getBody());
  }

  public UserAccountsResponse getAccountInfo(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(MONO_AUTH_HEADER_NAME, token);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<UserAccountsResponse> exchange = restTemplate.exchange(baseUrl + clientInfoUrl, HttpMethod.GET, entity, UserAccountsResponse.class);
    return exchange.getBody();
  }
}
