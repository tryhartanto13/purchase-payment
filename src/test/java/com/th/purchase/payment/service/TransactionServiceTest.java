package com.th.purchase.payment.service;

import com.th.purchase.payment.dto.TransactionRq;
import com.th.purchase.payment.dto.TransactionRs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.th.purchase.payment.dto.constant.PurchaseConstant.TRANSACTION_SUCCESS;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void storeTransaction_expectSuccess() {
        ReflectionTestUtils.setField(transactionService, "urlTransaction", "test.com");
        transactionService.storeTransaction(TransactionRq.builder()
                .transactionStatus(TRANSACTION_SUCCESS)
                .amount(new BigDecimal("100000"))
                .biller("GOPAY")
                .destNo("321321")
                .srcAccountNo("321321")
                .fee(new BigDecimal("1000"))
                .refNo("321")
                .destName("Mra").build()
        );
        Mockito.verify(restTemplate).postForEntity(any(String.class), any(TransactionRq.class), any());
    }

    @Test
    public void getTransaction_expectSuccess() {
        ReflectionTestUtils.setField(transactionService, "urlTransaction", "test.com");
        Mockito.when(restTemplate.postForEntity(any(String.class), any(TransactionRq.class), any())).thenReturn(ResponseEntity.ok(TransactionRs.builder()
                .transactionId(1L)
                .transactionStatus(1)
                .amount(new BigDecimal("100000"))
                .destName("Lala")
                .biller("GOPAY")
                .destNo("456789456")
                .srcAccountNo("3213134141")
                .fee(new BigDecimal("1000"))
                .refNo("3213211321")
                .build()));
        transactionService.getTransaction(TransactionRq.builder()
                .refNo("321").build()
        );
        Mockito.verify(restTemplate).postForEntity(any(String.class), any(TransactionRq.class), any());
    }
}
