package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.OrderEnum;
import com.example.dao.RouteDao;
import com.example.dao.RouteOrderDao;
import com.example.entity.HotelOrder;
import com.example.entity.Route;
import com.example.entity.RouteOrder;
import com.example.entity.SpotOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RouteOrderService {

    @Resource
    private RouteOrderDao routeOrderDao;
    @Resource
    private RouteDao routeDao;

    public void add(RouteOrder routeOrder) {

        routeOrder.setStatus(OrderEnum.STATUS_UNPAID.status);
        routeOrder.setTime(DateUtil.now());
        routeOrder.setOrderId(DateUtil.format(new Date(), "yyyyMMddHHmmss"));

        Route route = routeDao.selectById(routeOrder.getTourId());
        double routePrice = route.getPrice();
        routeOrder.setPrice(routePrice);
        route.setAmount(route.getAmount() - 1);
        routeDao.updateById(route);
        routeOrderDao.insert(routeOrder);
    }

    public List<RouteOrder> selectByUserId(Integer userId) {
        RouteOrder routeOrder = new RouteOrder();
        routeOrder.setUserId(userId);
        return routeOrderDao.selectAll(routeOrder);
    }

    /* 分页查询 */
    public PageInfo<RouteOrder> selectPage(RouteOrder routeOrder, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<RouteOrder> list = routeOrderDao.selectAll(routeOrder);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id) {
        routeOrderDao.deleteById(id);
    }

    public void pay(Integer id) {
        RouteOrder routeOrder = routeOrderDao.selectById(id);
        routeOrder.setStatus(OrderEnum.STATUS_PAID.status);
        routeOrderDao.updateById(routeOrder);
    }

    public void finished(Integer id) {
        RouteOrder routeOrder = routeOrderDao.selectById(id);
        routeOrder.setStatus(OrderEnum.STATUS_FINISHED.status);
        routeOrderDao.updateById(routeOrder);
    }
}
