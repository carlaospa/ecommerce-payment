package com.carlaospa.ecommerce.payment.service;

import com.carlaospa.ecommerce.payment.entity.PaymentEntity;
import com.carlaospa.ecommerce.checkout.event.CheckoutCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.carlaospa.ecommerce.payment.repository.PaymentRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements  PaymentService{

    private final PaymentRepository paymentRepository;

    @Override
    public Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent) {

        final PaymentEntity paymentEntity = PaymentEntity.builder()
                .checkoutCode((String) checkoutCreatedEvent.getCheckoutCode())
                .code(UUID.randomUUID().toString())
                .build();

        paymentRepository.save(paymentEntity);
        return Optional.of(paymentEntity);
    }
}
