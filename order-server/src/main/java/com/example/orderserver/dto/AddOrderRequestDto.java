package com.example.orderserver.dto;

import lombok.Data;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 22:23
 */
@Data
public class AddOrderRequestDto {
    private Long productId;
    private String orderPrice;
    private String shippingAddress;

}