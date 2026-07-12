package com.example.dao;

import com.example.entity.Params;
import com.example.entity.Route;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RouteDao extends Mapper<Route> {

    List<Route> findBySearch(@Param("params") Params params);

    @Select("select * from route")
    List<Route> findAll();

    @Select("select * from route where tourId = #{tourId}")
    Route selectById(Integer tourId);

    int updateById(Route route);
}
