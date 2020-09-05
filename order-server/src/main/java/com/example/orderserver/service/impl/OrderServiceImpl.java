package com.example.orderserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.orderserver.common.CommonPage;
import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.common.JException;
import com.example.orderserver.dao.OrderDaoMapper;
import com.example.orderserver.dto.AddOrderRequestDto;
import com.example.orderserver.dto.UpdateProductByProductIdRequestDto;
import com.example.orderserver.po.OrderPo;
import com.example.orderserver.po.ProductPo;
import com.example.orderserver.service.OrderService;
import com.example.orderserver.service.ProductApi;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 17:09
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private OrderDaoMapper orderDaoMapper;
    @Resource
    private ProductApi productApi;


    @Override
    public CommonReturnType list(Integer pageNum, Integer pageSize) {
        QueryWrapper<OrderPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderPo::getUserId, 1);
        PageHelper.startPage(pageNum,pageSize);
        List<OrderPo> orderList = orderDaoMapper.selectList(queryWrapper);

        return CommonReturnType.creat(CommonPage.restPage(orderList));
    }

    @Override
    @Transactional( rollbackFor = JException.class)
    public CommonReturnType add(AddOrderRequestDto reqDto) throws JException {
        String orderPrice = reqDto.getOrderPrice();
        Long productId = reqDto.getProductId();
        String shippingAddress = reqDto.getShippingAddress();
        //Get current user information
        Long userId= 1L;


        /** 1 Get lock */
        long orderId = IdUtil.getSnowflake(1, 1).nextId();
        String key = "ordSystem:product:productId:"+productId;
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();

            /** 2 Query product information */
            CommonReturnType<ProductPo> getProductResult = productApi.get(productId);
            String getProductCode = getProductResult.getCode();
            if(StringUtils.isBlank(getProductCode)|| !"200".equals(getProductCode)){
                return CommonReturnType.creat(null,"500","Service call failed");
            }
            ProductPo productPo = getProductResult.getData();
            if(ObjectUtils.isEmpty(productPo) ){
                return CommonReturnType.creat(null,"500","Products that don't exist");
            }
            if(productPo.getSurplusStock()==0 ){
                return CommonReturnType.creat(null,"500","The product is not in stock");
            }
            if(new BigDecimal(productPo.getProductPrice()).compareTo(new BigDecimal(orderPrice))!=0){
                return CommonReturnType.creat(null,"500","Order price error");
            }

            /** 3 Product inventory minus one */
            UpdateProductByProductIdRequestDto updateProductByProductIdRequestDto = new UpdateProductByProductIdRequestDto();
            updateProductByProductIdRequestDto.setProductId(productPo.getProductId());
            CommonReturnType updateProductResult = productApi.updateProductByProductId(updateProductByProductIdRequestDto);
            String updateProductCode = updateProductResult.getCode();
            if(StringUtils.isBlank(updateProductCode)|| !"200".equals(updateProductCode)){
                return CommonReturnType.creat(null,"500","Service call failed");
            }
            /** 4 Add order information */
            int insertNumber = orderDaoMapper.insert(OrderPo.builder()
                    .orderId(orderId)
                    .orderPrice(orderPrice)
                    .userId(userId)
                    .shippingAddress(shippingAddress)
                    .productId(productPo.getProductId())
                    .productName(productPo.getProductName())
                    .orderStatus(0)
                    .build());
            if(insertNumber==0){
                throw new JException("500","System abnormality");
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new JException("500", ex.getMessage());
        } finally {
            lock.unlock();
        }


        Map<String,Object> result = Maps.newHashMap();
        result.put("orderId",orderId);
        return CommonReturnType.creat(result);
    }

}