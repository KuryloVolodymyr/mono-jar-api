package com.example.monobank.service;

import com.example.monobank.client.MonoBankClient;
import com.example.monobank.model.AggregatedTransactionsInfo;
import com.example.monobank.model.JarStatisticResponse;
import com.example.monobank.model.mono.MonoTransactionDetails;
import com.example.monobank.model.TransactionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
//todo return amounts in hryvnias instead of coins
public class JarTransactionsService {

  private final MonoBankClient monoBankClient;

  public JarStatisticResponse getJarStatistics(String jarId, String startTime, String endTime) {
    Map<String, AggregatedTransactionsInfo> donates = getDonates(jarId, startTime, endTime);

    Collection<AggregatedTransactionsInfo> transactionDetails = donates.values();

    JarStatisticResponse jarStatisticResponse = new JarStatisticResponse();
    jarStatisticResponse.setTransactions(transactionDetails.stream().sorted().collect(Collectors.toList()));
    jarStatisticResponse.setTotalAmount(BigDecimal.valueOf(transactionDetails.stream().mapToLong(t -> t.getTotalAmount().longValue()).sum()));

    return jarStatisticResponse;
  }

  private Map<String, AggregatedTransactionsInfo> getDonates(String jarId, String startTime, String endTime) {
    List<MonoTransactionDetails> transactionsDetails = monoBankClient.getJarTransactions(jarId, startTime, endTime);
    Map<String, AggregatedTransactionsInfo> donatesMap = new HashMap<>();

    for (MonoTransactionDetails transactionDetails : transactionsDetails) {
      String sender = transactionDetails.getDescription();
      AggregatedTransactionsInfo transactionsInfo;

      if (donatesMap.containsKey(sender)){
        transactionsInfo = donatesMap.get(sender);
      } else {
        transactionsInfo = new AggregatedTransactionsInfo();
      }

      donatesMap.put(sender, transactionsInfo.addTransaction(TransactionInfo.of(transactionDetails)));
    }
    return donatesMap;
  }
}
