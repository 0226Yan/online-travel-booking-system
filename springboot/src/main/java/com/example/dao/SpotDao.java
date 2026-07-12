package com.example.dao;

import com.example.entity.Params;
import com.example.entity.Spot;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SpotDao extends Mapper<Spot> {

    List<Spot> findBySearch(@Param("params")Params params);

    @Select("select * from spot")
    List<Spot> findAll();

    @Select("select * from spot where spotId = #{spotId}")
    Spot selectById(Integer spotId);

    int updateById(Spot spot);
}
