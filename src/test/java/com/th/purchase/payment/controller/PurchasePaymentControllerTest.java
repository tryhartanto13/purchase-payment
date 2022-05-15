package com.th.purchase.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.th.purchase.payment.dto.PurchasePayRq;
import com.th.purchase.payment.exception.GeneralException;
import com.th.purchase.payment.service.PurchasePaymentService;
import com.th.purchase.payment.service.TransactionService;
import com.th.purchase.payment.service.UserAuditService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PurchasePaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchasePaymentService purchasePaymentService;

    @SneakyThrows
    @Test
    public void purchasePayment_expectSuccess() {
        mockMvc.perform(post("/v1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurchasePayRq.builder()
                                .refNo("321321").build()
                        )))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void purchasePayment_expectError() {
        doThrow(GeneralException.class).when(purchasePaymentService).doPayment(any(PurchasePayRq.class));
        mockMvc.perform(post("/v1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurchasePayRq.builder()
                                .refNo("321321").build()
                        )))
                .andExpect(status().isBadRequest());
    }
}
