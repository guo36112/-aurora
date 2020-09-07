package com.example.orderserver.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;


@Data
@TableName("orders")
@Builder
public class OrderPo {

    @TableId(type = IdType.INPUT)
    private Long orderId;

    private String orderPrice;

    private Long userId;

    private String shippingAddress;

    private Long productId;
    @TableField(exist = false)
    private String productName;
    //0:To be paid  1:Paid
    private Integer orderStatus;


}