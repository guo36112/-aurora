package com.example.orderserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.orderserver.common.CommonPage;
import com.example.orderserver.common.CommonReturnType;
import com.example.orderserver.common.JException;
import com.example.orderserver.dao.OrderDaoMapper;
import com.example.orderserver.dto.AddOrderRequestDto;
import com.example.orderserver.dto.ReduceInventoryByProductIdRequestDto;
import com.example.orderserver.dto.UpdateShippingAddressRequestDto;
import com.example.orderserver.po.OrderPo;
import com.example.orderserver.po.ProductPo;
import com.example.orderserver.po.UserPo;
import com.example.orderserver.service.OrderService;
import com.example.orderserver.service.ProductApi;
import com.example.orderserver.service.UserApi;
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

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private OrderDaoMapper orderDaoMapper;
    @Resource
    private ProductApi productApi;
    @Resource
    private UserApi userApi;


    @Override
    public CommonReturnType list(Integer pageNum, Integer pageSize) {
        QueryWrapper<OrderPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderPo::getUserId, 1);
        PageHelper.startPage(pageNum,pageSize);
        List<OrderPo> orderList = orderDaoMapper.selectList(queryWrapper);
        for(int i =0;i<orderList.size();i++){
            OrderPo orderPo = orderList.get(i);

            CommonReturnType<ProductPo> getProductResult = productApi.get(orderPo.getProductId());
            String getProductCode = getProductResult.getCode();
            if(StringUtils.isBlank(getProductCode)|| !"200".equals(getProductCode)){
                log.error("Service call failed");
                continue;
            }
            ProductPo productPo = getProductResult.getData();
            if(ObjectUtils.isEmpty(productPo) ){
                log.error("Products that don't exist");
                continue;
            }
            orderPo.setProductName(productPo.getProductName());
            orderList.set(i, orderPo);
        }

        return CommonReturnType.creat(CommonPage.restPage(orderList));
    }

    @Override
    @Transactional( rollbackFor = JException.class)
    public CommonReturnType add(AddOrderRequestDto reqDto) throws JException {
        String orderPrice = reqDto.getOrderPrice();
        Long productId = reqDto.getProductId();
        String shippingAddress = reqDto.getShippingAddress();
        //Get current user information
        CommonReturnType<UserPo> getUserResult = userApi.get(1L);
        String getUserCode = getUserResult.getCode();
        if(StringUtils.isBlank(getUserCode)|| !"200".equals(getUserCode)){
            return CommonReturnType.creat(null,"500","Service call failed");
        }
        UserPo userPo = getUserResult.getData();
        if(ObjectUtils.isEmpty(userPo) ){
            return CommonReturnType.creat(null,"500","Products that don't exist");
        }


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
            if(productPo.getStockNumber()==0 ){
                return CommonReturnType.creat(null,"500","The product is not in stock");
            }
            if(new BigDecimal(productPo.getProductPrice()).compareTo(new BigDecimal(orderPrice))!=0){
                return CommonReturnType.creat(null,"500","Order price error");
            }

            /** 3 Product inventory minus one */
            ReduceInventoryByProductIdRequestDto updateProductByProductIdRequestDto = new ReduceInventoryByProductIdRequestDto();
            updateProductByProductIdRequestDto.setProductId(productPo.getProductId());
            CommonReturnType updateProductResult = productApi.reduceInventoryByProductId(updateProductByProductIdRequestDto);
            String updateProductCode = updateProductResult.getCode();
            if(StringUtils.isBlank(updateProductCode)|| !"200".equals(updateProductCode)){
                return CommonReturnType.creat(null,"500","Service call failed");
            }
            /** 4 Add order information */
            int insertNumber = orderDaoMapper.insert(OrderPo.builder()
                    .orderId(orderId)
                    .orderPrice(orderPrice)
                    .userId(userPo.getUserId())
                    .shippingAddress(shippingAddress)
                    .productId(productPo.getProductId())
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



        if(!userPo.getShippingAddress().equals(shippingAddress)){
            CommonReturnType<UserPo> updateUserResult = userApi.updateShippingAddress(UpdateShippingAddressRequestDto.builder()
                    .userId(userPo.getUserId())
                    .shippingAddress(shippingAddress)
                    .build());
            String getProductCode = updateUserResult.getCode();
            if(StringUtils.isBlank(getProductCode)|| !"200".equals(getProductCode)){
                log.error("Service call failed");
            }
        }


        Map<String,Object> result = Maps.newHashMap();
        result.put("orderId",orderId);
        return CommonReturnType.creat(result);
    }

}