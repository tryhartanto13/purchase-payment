package com.th.purchase.payment.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralException extends RuntimeException {
    private String errCode;
    private String errDesc;
    private String refNo;
}
