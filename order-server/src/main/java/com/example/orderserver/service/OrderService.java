package com.example.orderserver.service;

import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.common.JException;
import com.example.orderserver.dto.AddOrderRequestDto;

public interface OrderService {

    CommonReturnType list(Integer pageNum, Integer pageSize);

    CommonReturnType add( AddOrderRequestDto reqDto) throws JException;



}