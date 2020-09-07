package com.example.orderserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("users")
@Builder
public class UserPo {

    @TableId
    private Long userId;

    private String userName;

    private String shippingAddress;

}