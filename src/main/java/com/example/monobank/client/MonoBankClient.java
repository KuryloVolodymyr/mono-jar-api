package com.example.monobank.client;

import com.example.monobank.model.mono.MonoTransactionDetails;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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

  public static final Long MONTH_IN_MILLIS = 2_592_000L;

  private final RestTemplate restTemplate;

  public List<MonoTransactionDetails> getJarTransactions(String accountId, LocalDateTime startDate, LocalDateTime endDate) {

    long endTimestamp = endDate.toEpochSecond(ZoneOffset.UTC);
    long startTimestamp = startDate.toEpochSecond(ZoneOffset.UTC);

    List<MonoTransactionDetails> monoTransactions = new ArrayList<>();


    //todo MAKE PRETTY
    do {
      if (startTimestamp + MONTH_IN_MILLIS > endTimestamp){
        monoTransactions.addAll(getTransactionBatchInValidTimeRange(accountId, startTimestamp, endTimestamp));
      } else {
        monoTransactions.addAll(getTransactionBatchInValidTimeRange(accountId, startTimestamp, startTimestamp + MONTH_IN_MILLIS));
      }
      startTimestamp = startTimestamp + MONTH_IN_MILLIS;
    } while (endTimestamp - startTimestamp > MONTH_IN_MILLIS);

    return monoTransactions;
  }

  public UserAccountsResponse getAccountInfo(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(MONO_AUTH_HEADER_NAME, token);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<UserAccountsResponse> exchange = restTemplate.exchange(baseUrl + clientInfoUrl, HttpMethod.GET, entity, UserAccountsResponse.class);
    return exchange.getBody();
  }


  private List<MonoTransactionDetails> getTransactionBatchInValidTimeRange(String accountId, Long startTimestamp, Long endTimestamp) {
    List<MonoTransactionDetails> monoTransactionDetailsInValidTimeRange = new ArrayList<>();
    List<MonoTransactionDetails> transactionBatch;

    do {
      transactionBatch = getTransactionsBatch(accountId, startTimestamp, endTimestamp);
      monoTransactionDetailsInValidTimeRange.addAll(transactionBatch);
    } while (transactionBatch.size() == 500);

    return monoTransactionDetailsInValidTimeRange;

  }

  private List<MonoTransactionDetails> getTransactionsBatch(String accountId, Long startTimestamp, Long endTimestamp) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(MONO_AUTH_HEADER_NAME, token);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);

    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("accountId", accountId);
    uriParams.put("startTime", String.valueOf(startTimestamp));
    uriParams.put("endTime", String.valueOf(endTimestamp));

    ResponseEntity<MonoTransactionDetails[]> exchange = restTemplate.exchange(baseUrl + statementsUrl, HttpMethod.GET, entity, MonoTransactionDetails[].class, uriParams);
    return List.of(exchange.getBody());
  }

}
