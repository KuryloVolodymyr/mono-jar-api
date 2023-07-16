package com.example.monobank.controller;

import com.example.monobank.model.JarCommentsResponse;
import com.example.monobank.model.JarStatisticResponse;
import com.example.monobank.service.JarTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/jar/info")
@RequiredArgsConstructor
public class JarInfoController {

  private final JarTransactionsService jarReadingService;

  @GetMapping(value = "/donates/{jarId}/{startTime}")
  public JarStatisticResponse getAllDonates(@PathVariable String jarId, @PathVariable String startTime){
    return jarReadingService.getJarStatistics(jarId, startTime);
  }

  @GetMapping(value = "/donates/comments/{jarId}/{startTime}")
  public List<JarCommentsResponse> getDonatesCommented(@PathVariable String jarId, @PathVariable String startTime){
    return jarReadingService.getJarComments(jarId, startTime);
  }
}
