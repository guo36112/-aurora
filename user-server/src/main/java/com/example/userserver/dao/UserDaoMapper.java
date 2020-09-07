package com.example.userserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userserver.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDaoMapper extends BaseMapper<UserPo> {



}