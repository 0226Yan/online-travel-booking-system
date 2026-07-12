package com.example.dao;

import com.example.entity.RouteOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteOrderDao {

    int insert(RouteOrder routeOrder);

    List<RouteOrder> selectAll(RouteOrder routeOrder);

    int deleteById(Integer id);

    @Select("select * from route_order where id = #{id}")
    RouteOrder selectById(Integer id);

    int updateById(RouteOrder routeOrder);
}
