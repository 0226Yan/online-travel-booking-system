package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.OrderEnum;
import com.example.dao.FlightDao;
import com.example.dao.FlightOrderDao;
import com.example.entity.Flight;
import com.example.entity.FlightOrder;
import com.example.entity.SpotOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FlightOrderService {

    @Resource
    private FlightOrderDao flightOrderDao;
    @Resource
    private FlightDao flightDao;

    public void add(FlightOrder flightOrder) {

        flightOrder.setStatus(OrderEnum.STATUS_UNPAID.status);
        flightOrder.setTime(DateUtil.now());
        flightOrder.setOrderId(DateUtil.format(new Date(), "yyyyMMddHHmmss"));

        Flight flight = flightDao.selectById(flightOrder.getFlightId());
        double flightPrice = flight.getPrice();
        flightOrder.setPrice(flightPrice);
        flight.setVacantNum(flight.getVacantNum() - 1);
        flightDao.updateById(flight);
        flightOrderDao.insert(flightOrder);
    }

    public List<FlightOrder> selectByUserId(Integer userId) {
        FlightOrder flightOrder = new FlightOrder();
        flightOrder.setUserId(userId);
        return flightOrderDao.selectAll(flightOrder);
    }

    /* 分页查询 */
    public PageInfo<FlightOrder> selectPage(FlightOrder flightOrder, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<FlightOrder> list = flightOrderDao.selectAll(flightOrder);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id) {
        flightOrderDao.deleteById(id);
    }

    public void pay(Integer id) {
        FlightOrder flightOrder = flightOrderDao.selectById(id);
        flightOrder.setStatus(OrderEnum.STATUS_PAID.status);
        flightOrderDao.updateById(flightOrder);
    }

    public void finished(Integer id) {
        FlightOrder flightOrder = flightOrderDao.selectById(id);
        flightOrder.setStatus(OrderEnum.STATUS_FINISHED.status);
        flightOrderDao.updateById(flightOrder);
    }
}
