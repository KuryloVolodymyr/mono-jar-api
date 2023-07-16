package com.example.monobank.service;

import com.example.monobank.client.MonoBankClient;
import com.example.monobank.model.TransactionDetails;
import com.example.monobank.model.JarCommentsResponse;
import com.example.monobank.model.JarStatisticResponse;
import com.example.monobank.model.JarTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JarTransactionsService {

  private final MonoBankClient monoBankClient;

  private MultiValueMap<String, TransactionDetails> comments = new LinkedMultiValueMap<>();
  private MultiValueMap<String, BigDecimal> donates = new LinkedMultiValueMap<>();

  public JarStatisticResponse getJarStatistics(String jarId, String startTime) {
    refreshInfo(jarId, startTime);

    Set<String> donatesSenders = donates.keySet();
    List<TransactionDetails> transactionDetails = new ArrayList<>();

    for (String sender : donatesSenders) {
      BigDecimal totalDonate = BigDecimal.valueOf(donates.get(sender).stream().mapToLong(t -> t.longValue()).sum());
      BigDecimal totalAmount = calculateAmount(totalDonate);
      transactionDetails.add(new TransactionDetails(sender, totalAmount, null));
    }

    JarStatisticResponse jarStatisticResponse = new JarStatisticResponse();

    jarStatisticResponse.setTransactions(transactionDetails.stream().sorted().collect(Collectors.toList()));
    jarStatisticResponse.setTotalAmount(BigDecimal.valueOf(transactionDetails.stream().mapToLong(t -> t.getAmount().longValue()).sum()));
    return jarStatisticResponse;
  }

  public List<JarCommentsResponse> getJarComments(String jarId, String startTime) {
    refreshInfo(jarId, startTime);

    Set<String> donatesSenders = comments.keySet();
    List<JarCommentsResponse> jarCommentsResponses = new ArrayList<>();
    for (String sender : donatesSenders) {
      jarCommentsResponses.add(new JarCommentsResponse(sender, comments.get(sender)));
    }
    return jarCommentsResponses;
  }

  private BigDecimal calculateAmount(BigDecimal amount) {
    return amount;
  }

  private void refreshInfo(String jarId, String startTime) {
    log.info("initiated");

    List<JarTransaction> jarTransactions = monoBankClient.getJarTransactions(jarId, startTime);

    MultiValueMap<String, BigDecimal> donatesMap = new LinkedMultiValueMap<>();
    MultiValueMap<String, TransactionDetails> commentsMap = new LinkedMultiValueMap<>();

    for (JarTransaction jarResponse : jarTransactions) {
      donatesMap.add(jarResponse.getDescription(), jarResponse.getAmount());

      if (Objects.nonNull(jarResponse.getComment())) {
        BigDecimal amount = calculateAmount(jarResponse.getAmount());
        commentsMap.add(jarResponse.getDescription(), new TransactionDetails(jarResponse.getDescription(), amount, jarResponse.getComment()));
      }
    }

    donates = donatesMap;
    comments = commentsMap;
  }
}
