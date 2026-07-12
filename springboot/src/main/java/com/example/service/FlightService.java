package com.example.service;

import com.example.dao.FlightDao;
import com.example.entity.Flight;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class FlightService {

    @Resource
    private FlightDao flightDao;

    public PageInfo<Flight> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<Flight> list = flightDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Flight flight) {
        flightDao.insertSelective(flight);
    }

    public void update(Flight flight) {
        flightDao.updateByPrimaryKeySelective(flight);
    }

    public void delete(Integer flightId) {
        flightDao.deleteByPrimaryKey(flightId);
    }

    public List<Flight> selectAll() {
        return flightDao.findAll();
    }

    public Flight selectById(Integer flightId) {
        return flightDao.selectById(flightId);
    }
}
