package com.carlaospa.ecommerce.payment.service;

import com.carlaospa.ecommerce.payment.entity.PaymentEntity;
import com.carlaospa.ecommerce.checkout.event.CheckoutCreatedEvent;

import java.util.Optional;


public interface PaymentService {

    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
