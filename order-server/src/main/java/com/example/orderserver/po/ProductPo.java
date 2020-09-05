package com.example.orderserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("product_info")
public class ProductPo {

    @TableId
    private Long productId;

    private String productName;

    private String productPrice;

    private Integer surplusStock;

}