package com.example.dao;

import com.example.entity.Admin;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {
    List<Admin> findBySearch(@Param("params") Params params);

    @Select("select * from admin where username = #{username} limit 1")
    Admin findByName(@Param("username") String username);

    @Select("select * from admin where username = #{username} and password = #{password}")
    Admin findByNameAndPassword(@Param("username")String username, @Param("password")String password);
}
