package com.example.productserver.controller;

import com.example.productserver.common.CommonReturnType;
import com.example.productserver.dto.ReduceInventoryByProductIdRequestDto;
import com.example.productserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/list")
    public CommonReturnType list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return productService.list(pageNum,pageSize);
    }

    @GetMapping("/get/{productId}")
    public CommonReturnType get(@PathVariable("productId") Long productId) {
        return productService.get(productId);
    }

    @PostMapping("/reduceInventoryByProductId")
    public CommonReturnType reduceInventoryByProductId(@RequestBody ReduceInventoryByProductIdRequestDto reqDto) {
        return productService.reduceInventoryByProductId(reqDto);
    }





}