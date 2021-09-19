package com.carlaospa.ecommerce.payment.listener;

import com.carlaospa.ecommerce.payment.entity.PaymentEntity;
import com.carlaospa.ecommerce.payment.service.PaymentService;
import com.carlaospa.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.carlaospa.ecommerce.payment.event.PaymentCreatedEvent;
import com.carlaospa.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event){

       log.info("checkoutCreatedEvent={}", event);
        final PaymentEntity paymentEntity = paymentService.create(event).orElseThrow();
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());

    }
}
