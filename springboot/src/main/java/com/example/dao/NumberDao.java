package com.example.dao;

import com.example.entity.Params;
import com.example.entity.Number;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NumberDao extends Mapper<Number> {

    List<Number> findBySearch(@Param("params")Params params);

/*    @Select("select * from room where hotelId = #{hotelId}")
    List<Room> selectByHotelId(Integer hotelId);

    Room selectById(Integer roomId);

    int updateById(Room room);*/
}
