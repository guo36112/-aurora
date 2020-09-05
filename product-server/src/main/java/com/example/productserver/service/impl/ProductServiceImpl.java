package com.example.productserver.service.impl;

import com.example.productserver.common.CommonPage;
import com.example.productserver.common.CommonReturnType;
import com.example.productserver.common.JException;
import com.example.productserver.dao.ProductDaoMapper;
import com.example.productserver.dto.UpdateProductByProductIdRequestDto;
import com.example.productserver.po.ProductPo;
import com.example.productserver.service.ProductService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:09
 */
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductDaoMapper productDaoMapper;



    @Override
    public CommonReturnType list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductPo> productList = productDaoMapper.selectList(null);

        return CommonReturnType.creat(CommonPage.restPage(productList));
    }

    @Override
    public CommonReturnType get(Long productId) {
        ProductPo productPo = productDaoMapper.selectById(productId);

        return CommonReturnType.creat(productPo);
    }

    @Override
    public CommonReturnType updateProductByProductId(UpdateProductByProductIdRequestDto reqDto) {
        if(ObjectUtils.isEmpty(reqDto.getProductId())){
            return CommonReturnType.fail();
        }

        int updateNumber = productDaoMapper.updateProductByProductId(reqDto.getProductId());
        if (updateNumber == 0) {
            return CommonReturnType.creat(null,"500","Inventory cannot be negative");
        }
        return CommonReturnType.creat(null);
    }


}