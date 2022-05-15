package com.th.purchase.payment.service;

import com.th.purchase.payment.dto.*;
import com.th.purchase.payment.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.th.purchase.payment.dto.constant.PurchaseConstant.ErrorCode.*;
import static com.th.purchase.payment.dto.constant.PurchaseConstant.*;

@Service
@Slf4j
public class PurchasePaymentService {
    @Autowired
    private UserAuditService userAuditService;
    @Autowired
    private TransactionService transactionService;

    public PurchasePayRs doPayment(PurchasePayRq purchasePayRq) {
        Result result = Result.builder().result(SUCCESS).build();
        PurchasePayRs purchasePayRs = null;
        TransactionRq transactionRq = TransactionRq.builder().refNo(purchasePayRq.getRefNo()).build();
        TransactionRs transactionRs = transactionService.getTransaction(transactionRq);
        try {
            pay3Rd(transactionRq, transactionRs);
            purchasePayRs = PurchasePayRs.builder().destNo(transactionRq.getDestNo())
                    .refNo(transactionRq.getRefNo())
                    .destNo(transactionRs.getDestNo())
                    .destName(transactionRs.getDestName())
                    .fee(transactionRs.getFee())
                    .amount(transactionRs.getAmount())
                    .transactionStatus(SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Exception happened when purchase inquiry, message: [{}]", ex.getMessage());
            throw errorHandler(ex, result);
        } finally {
            transactionService.storeTransaction(transactionRq);
            CompletableFuture.runAsync(() -> userAuditService.send(UserAuditRq.builder().srcAcctNo(transactionRs.getSrcAccountNo())
                    .destNo(transactionRs.getDestNo())
                    .activity(PAYMENT_ACTIVITY)
                    .refNo(transactionRs.getRefNo())
                    .result(result.getResult()).build()));
        }
        return purchasePayRs;
    }

    private void pay3Rd(TransactionRq transactionRq, TransactionRs transactionRs) {
        transactionRq.setTransactionId(transactionRs.getTransactionId());
        if (transactionRs.getAmount().compareTo(new BigDecimal("10000")) == 1) {
            transactionRq.setTransactionStatus(TRANSACTION_SUCCESS);
        } else {
            transactionRq.setTransactionStatus(TRANSACTION_FAILED);
            throw GeneralException.builder().refNo(transactionRq.getRefNo())
                    .errCode(SRC_SYSTEM.concat("-").concat(ERR_CODE))
                    .errDesc(ERR_DESC).build();
        }
    }

    private GeneralException errorHandler(Exception ex, Result result) {
        String errCode = DEFAULT_ERR_CODE;
        String errDesc = DEFAULT_ERR_DESC;
        String refNo = "";
        if (ex instanceof GeneralException) {
            errCode = ((GeneralException) ex).getErrCode();
            errDesc = ((GeneralException) ex).getErrDesc();
            refNo = ((GeneralException) ex).getRefNo();
        }
        result.setResult(errCode);
        return GeneralException.builder().errCode(errCode).errDesc(errDesc).refNo(refNo).build();
    }
}
