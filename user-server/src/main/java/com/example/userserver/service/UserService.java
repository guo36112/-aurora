package com.example.userserver.service;


import com.example.userserver.common.CommonReturnType;
import com.example.userserver.dto.UpdateShippingAddressRequestDto;

public interface UserService {

    CommonReturnType get(Long userId);

    CommonReturnType updateShippingAddress( UpdateShippingAddressRequestDto reqDto);



}