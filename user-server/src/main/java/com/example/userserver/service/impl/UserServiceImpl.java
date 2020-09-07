package com.example.userserver.service.impl;

import com.example.userserver.common.CommonReturnType;
import com.example.userserver.dao.UserDaoMapper;
import com.example.userserver.dto.UpdateShippingAddressRequestDto;
import com.example.userserver.po.UserPo;
import com.example.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDaoMapper userDaoMapper;


    @Override
    public CommonReturnType get(Long userId) {
        UserPo userPo = userDaoMapper.selectById(userId);

        return CommonReturnType.creat(userPo);
    }

    @Override
    public CommonReturnType updateShippingAddress(UpdateShippingAddressRequestDto reqDto) {
        int i = userDaoMapper.updateById(UserPo.builder().userId(reqDto.getUserId()).shippingAddress(reqDto.getShippingAddress()).build());
        if(i==0){
            return CommonReturnType.fail();
        }

        return CommonReturnType.creat(null);
    }


}