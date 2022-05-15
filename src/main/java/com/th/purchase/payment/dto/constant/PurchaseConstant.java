package com.th.purchase.payment.dto.constant;

public class PurchaseConstant {
    public static final String SUCCESS = "SUCCESS";
    public static final int TRANSACTION_FAILED = 0;
    public static final int TRANSACTION_SUCCESS = 2;
    public static final String PAYMENT_ACTIVITY = "Purchase Payment";
    public static class ErrorCode {
        private ErrorCode(){}
        public static final String SRC_SYSTEM="3RD";
        public static final String ERR_CODE = "99";
        public static final String ERR_DESC = "Invalid Transaction";
        public static final String DEFAULT_ERR_CODE="INT-UE";
        public static final String DEFAULT_ERR_DESC="Unknown Error";
    }
}
