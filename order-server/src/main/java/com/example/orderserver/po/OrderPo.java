package com.example.orderserver.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Program: ordSystem
 * @Description:
 * @Author: admin
 * @Create: 2020/09/02 16:57
 */
@Data
@ToString
@TableName("order_info")
@Builder
public class OrderPo {

    @TableId(type = IdType.INPUT)
    private Long orderId;

    private String orderPrice;

    private Long userId;

    private String shippingAddress;

    private Long productId;

    private String productName;
    //0:To be paid  1:Paid
    private Integer orderStatus;


}