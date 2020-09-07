package com.example.productserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.productserver.po.ProductPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductDaoMapper extends BaseMapper<ProductPo> {

    @Update("update products set stock_number=stock_number-1 where product_id=#{productId} and stock_number>0 ")
    int reduceInventoryByProductId(Long productId);



}