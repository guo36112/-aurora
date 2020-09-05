package com.example.productserver.service;


import com.example.productserver.common.CommonPage;
import com.example.productserver.common.CommonReturnType;
import com.example.productserver.dto.UpdateProductByProductIdRequestDto;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:09
 */
public interface ProductService {

    CommonReturnType list(Integer pageNum, Integer pageSize);

    CommonReturnType get( Long productId);

    CommonReturnType updateProductByProductId( UpdateProductByProductIdRequestDto reqDto);

}