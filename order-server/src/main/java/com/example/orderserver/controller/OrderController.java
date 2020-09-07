package com.example.orderserver.controller;

import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.dto.AddOrderRequestDto;
import com.example.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;




    @GetMapping("/list")
    public CommonReturnType list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return orderService.list(pageNum,pageSize);
    }

    @PostMapping("/add")
    public CommonReturnType add(@RequestBody AddOrderRequestDto reqDto) {
        return orderService.add(reqDto);
    }



}