package com.example.dao;

import com.example.entity.FlightOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightOrderDao {

    int insert(FlightOrder flightOrder);

    List<FlightOrder> selectAll(FlightOrder flightOrder);

    int deleteById(Integer id);

    @Select("select * from flight_order where id = #{id}")
    FlightOrder selectById(Integer id);

    int updateById(FlightOrder flightOrder);
}
