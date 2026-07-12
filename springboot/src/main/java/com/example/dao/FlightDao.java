package com.example.dao;

import com.example.entity.Flight;
import com.example.entity.Params;
import com.example.entity.Spot;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlightDao extends Mapper<Flight> {

    List<Flight> findBySearch(@Param("params") Params params);

    @Select("select * from flight")
    List<Flight> findAll();

    @Select("select * from flight where flightId = #{flightId}")
    Flight selectById(Integer flightId);

    int updateById(Flight flight);
}
