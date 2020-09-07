package com.example.orderserver.service;

import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.dto.UpdateShippingAddressRequestDto;
import com.example.orderserver.po.UserPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-server")
public interface UserApi {

    @RequestMapping(value = "/get/{userId}",method = RequestMethod.GET)
    CommonReturnType<UserPo> get(@PathVariable Long userId) ;

    @RequestMapping(value = "/updateShippingAddress",method = RequestMethod.POST)
    CommonReturnType updateShippingAddress(@RequestBody UpdateShippingAddressRequestDto reqDto) ;

}