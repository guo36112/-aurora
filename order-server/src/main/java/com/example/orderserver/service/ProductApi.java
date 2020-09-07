package com.example.orderserver.service;

import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.dto.ReduceInventoryByProductIdRequestDto;
import com.example.orderserver.po.ProductPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "product-server")
public interface ProductApi {

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    String list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize);

    @RequestMapping(value = "/get/{productId}",method = RequestMethod.GET)
    CommonReturnType<ProductPo> get(@PathVariable Long productId) ;

    @RequestMapping(value = "/reduceInventoryByProductId",method = RequestMethod.POST)
    CommonReturnType reduceInventoryByProductId(@RequestBody ReduceInventoryByProductIdRequestDto reqDto);


}