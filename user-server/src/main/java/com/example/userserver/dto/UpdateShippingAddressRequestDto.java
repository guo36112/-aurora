package com.example.userserver.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateShippingAddressRequestDto {

    private Long userId;

    private String shippingAddress;




}