package com.example.dao;

import com.example.entity.HotelOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelOrderDao {

    int insert(HotelOrder hotelOrder);

    List<HotelOrder> selectAll(HotelOrder hotelOrder);

    int deleteById(Integer id);

    @Select("select * from hotel_order where id = #{id}")
    HotelOrder selectById(Integer id);

    int updateById(HotelOrder hotelOrder);

}
