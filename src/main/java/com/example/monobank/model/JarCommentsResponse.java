package com.example.monobank.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class JarCommentsResponse {
  private final String sender;
  private final List<AggregatedTransactionsInfo> comments;
}
