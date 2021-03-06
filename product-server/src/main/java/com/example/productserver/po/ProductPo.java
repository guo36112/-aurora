package com.example.productserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("products")
@Builder
public class ProductPo {

    @TableId
    private Long productId;

    private String productName;

    private String productPrice;

    private Integer stockNumber;

}