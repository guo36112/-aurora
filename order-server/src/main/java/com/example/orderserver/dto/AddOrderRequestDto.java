package com.example.orderserver.dto;

import lombok.Data;

@Data
public class AddOrderRequestDto {
    private Long productId;
    private String orderPrice;
    private String shippingAddress;

}