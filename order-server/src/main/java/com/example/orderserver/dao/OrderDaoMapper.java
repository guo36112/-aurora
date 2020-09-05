package com.example.orderserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.orderserver.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:17
 */
@Mapper
public interface OrderDaoMapper extends BaseMapper<OrderPo> {

}