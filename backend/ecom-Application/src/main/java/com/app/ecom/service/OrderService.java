package com.app.ecom.service;


import com.app.ecom.payload.OrderDTO;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.TypeRegistration;

public interface OrderService {

    @Transactional
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
