package com.example.monobank.controller;

import com.example.monobank.model.JarStatisticResponse;
import com.example.monobank.service.JarTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
@RequestMapping(value = "/jar/info")
@RequiredArgsConstructor
public class JarInfoController {

  private final JarTransactionsService jarReadingService;

  @GetMapping(value = "/donates/{jarId}/{startTime}/{endTime}")
  public JarStatisticResponse getAllDonates(@PathVariable String jarId,
                                            @PathVariable String startTime,
                                            @PathVariable String endTime){

    return jarReadingService.getJarStatistics(jarId, startTime, endTime);
  }
}
