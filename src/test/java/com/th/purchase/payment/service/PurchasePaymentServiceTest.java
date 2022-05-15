package com.th.purchase.payment.service;

import com.th.purchase.payment.dto.PurchasePayRq;
import com.th.purchase.payment.dto.TransactionRq;
import com.th.purchase.payment.dto.TransactionRs;
import com.th.purchase.payment.exception.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class PurchasePaymentServiceTest {
    @InjectMocks
    private PurchasePaymentService purchasePaymentService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private UserAuditService userAuditService;

    @Test
    public void doPayment_expectSuccess() {
        Mockito.when(transactionService.getTransaction(Mockito.any(TransactionRq.class))).thenReturn(TransactionRs.builder()
                        .transactionId(1L)
                        .transactionStatus(1)
                        .amount(new BigDecimal("100000"))
                        .destName("Lala")
                        .biller("GOPAY")
                        .destNo("456789456")
                        .srcAccountNo("3213134141")
                        .fee(new BigDecimal("1000"))
                        .refNo("3213211321")
                .build());
        purchasePaymentService.doPayment(PurchasePayRq.builder()
                .refNo("3213211321").build());
        Mockito.verify(transactionService).getTransaction(Mockito.any(TransactionRq.class));
        Mockito.verify(transactionService).storeTransaction(Mockito.any(TransactionRq.class));
    }

    @Test(expected = GeneralException.class)
    public void doPayment_expectError() {
        Mockito.when(transactionService.getTransaction(Mockito.any(TransactionRq.class))).thenReturn(TransactionRs.builder()
                .transactionId(1L)
                .transactionStatus(1)
                .amount(new BigDecimal("100"))
                .destName("Lala")
                .biller("GOPAY")
                .destNo("456789456")
                .srcAccountNo("3213134141")
                .fee(new BigDecimal("1000"))
                .refNo("3213211321")
                .build());
        purchasePaymentService.doPayment(PurchasePayRq.builder()
                .refNo("3213211321").build());
        Mockito.verify(transactionService).getTransaction(Mockito.any(TransactionRq.class));
        Mockito.verify(transactionService).storeTransaction(Mockito.any(TransactionRq.class));
    }
}
