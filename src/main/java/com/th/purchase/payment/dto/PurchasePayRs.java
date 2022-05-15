package com.th.purchase.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PurchasePayRs {
    private String refNo;
    private String destNo;
    private String destName;
    private BigDecimal amount;
    private BigDecimal fee;
    private String transactionStatus;
}
