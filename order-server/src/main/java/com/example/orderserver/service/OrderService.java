package com.example.orderserver.service;

import com.example.orderserver.common.CommonPage;
import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.common.JException;
import com.example.orderserver.dto.AddOrderRequestDto;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:09
 */
public interface OrderService {

    CommonReturnType list(Integer pageNum, Integer pageSize);

    CommonReturnType add( AddOrderRequestDto reqDto) throws JException;



}