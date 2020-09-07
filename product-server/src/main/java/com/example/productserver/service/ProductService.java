package com.example.productserver.service;


import com.example.productserver.common.CommonReturnType;
import com.example.productserver.dto.ReduceInventoryByProductIdRequestDto;

public interface ProductService {

    CommonReturnType list(Integer pageNum, Integer pageSize);

    CommonReturnType get( Long productId);

    CommonReturnType reduceInventoryByProductId( ReduceInventoryByProductIdRequestDto reqDto);

}