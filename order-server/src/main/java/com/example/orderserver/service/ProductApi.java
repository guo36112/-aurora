package com.example.orderserver.service;

import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.dto.AddOrderRequestDto;
import com.example.orderserver.dto.UpdateProductByProductIdRequestDto;
import com.example.orderserver.po.ProductPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Program: springcloud-demo
 * @Description:
 * @Author: guojunjie
 * @Create: 2020/05/28 20:54
 */
@FeignClient(value = "product-server")
public interface ProductApi {

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    String listProduct(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize);

    @RequestMapping(value = "/get/{productId}",method = RequestMethod.GET)
    CommonReturnType<ProductPo> get(@PathVariable Long productId) ;

    @RequestMapping(value = "/updateProductByProductId",method = RequestMethod.POST)
    CommonReturnType updateProductByProductId(@RequestBody UpdateProductByProductIdRequestDto reqDto);


}