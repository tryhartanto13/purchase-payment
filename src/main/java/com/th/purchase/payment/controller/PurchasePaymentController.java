package com.th.purchase.payment.controller;

import com.th.purchase.payment.dto.PurchasePayRq;
import com.th.purchase.payment.dto.PurchasePayRs;
import com.th.purchase.payment.service.PurchasePaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1")
@Slf4j
public class PurchasePaymentController {

    @Autowired
    private PurchasePaymentService ppService;

    @PostMapping(value = "/payment")
    @ResponseBody
    public PurchasePayRs payment(@RequestBody PurchasePayRq purchasePayRq) {
        return ppService.doPayment(purchasePayRq);
    }

}
