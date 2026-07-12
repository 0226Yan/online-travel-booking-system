package com.example.dao;

import com.example.entity.SpotOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotOrderDao {

    int insert(SpotOrder spotOrder);

    List<SpotOrder> selectAll(SpotOrder spotOrder);

    int deleteById(Integer id);

    @Select("select * from spot_order where id = #{id}")
    SpotOrder selectById(Integer id);

    int updateById(SpotOrder spotOrder);
}
