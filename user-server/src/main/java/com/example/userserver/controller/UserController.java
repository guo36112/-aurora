package com.example.userserver.controller;

import com.example.userserver.common.CommonReturnType;
import com.example.userserver.dto.UpdateShippingAddressRequestDto;
import com.example.userserver.po.UserPo;
import com.example.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{userId}")
    public CommonReturnType get(@PathVariable("userId") Long userId) {
        return userService.get(userId);
    }

    @RequestMapping(value = "/updateShippingAddress",method = RequestMethod.POST)
    public CommonReturnType updateShippingAddress(@RequestBody UpdateShippingAddressRequestDto reqDto){
        return userService.updateShippingAddress(reqDto);
    }

}