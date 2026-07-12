package com.example.service;

import com.example.dao.RouteDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.Route;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RouteService {

    @Resource
    private RouteDao routeDao;

    public void add(Route route) {
        routeDao.insertSelective(route);
    }

    public void update(Route route) {
        routeDao.updateByPrimaryKeySelective(route);
    }

    public PageInfo<Route> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<Route> list = routeDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(Integer tourId) {
        routeDao.deleteByPrimaryKey(tourId);
    }

    public List<Route> selectAll() {
        return routeDao.findAll();
    }

    public Route selectById(Integer tourId) {
        return routeDao.selectById(tourId);
    }
}
