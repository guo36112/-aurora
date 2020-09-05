package com.example.productserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.productserver.po.ProductPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:17
 */
@Mapper
@Repository
public interface ProductDaoMapper extends BaseMapper<ProductPo> {

    @Update("update product_info set surplus_stock=surplus_stock-1 where product_id=#{productId} and surplus_stock>0 ")
    int updateProductByProductId(Long productId);



}