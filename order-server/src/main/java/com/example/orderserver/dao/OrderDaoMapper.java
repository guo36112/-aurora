package com.example.orderserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.orderserver.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderDaoMapper extends BaseMapper<OrderPo> {

}