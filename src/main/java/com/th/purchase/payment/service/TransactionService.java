package com.th.purchase.payment.service;

import com.th.purchase.payment.dto.TransactionRq;
import com.th.purchase.payment.dto.TransactionRs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.transaction}")
    private String urlTransaction;

    public void storeTransaction(TransactionRq transactionRq) {
        restTemplate.postForEntity(urlTransaction.concat("/v1/storetrx"), transactionRq, HttpStatus.class);
    }

    public TransactionRs getTransaction(TransactionRq transactionRq) {
        return restTemplate.postForEntity(urlTransaction.concat("/v1/gettrx"), transactionRq, TransactionRs.class).getBody();
    }
}
