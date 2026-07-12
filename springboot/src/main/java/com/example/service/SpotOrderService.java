package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.OrderEnum;
import com.example.dao.SpotDao;
import com.example.dao.SpotOrderDao;
import com.example.entity.Spot;
import com.example.entity.SpotOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SpotOrderService {

    @Resource
    private SpotOrderDao spotOrderDao;
    @Resource
    private SpotDao spotDao;

    public void add(SpotOrder spotOrder) {

        spotOrder.setStatus(OrderEnum.STATUS_UNPAID.status);
        spotOrder.setTime(DateUtil.now());
        spotOrder.setOrderId(DateUtil.format(new Date(), "yyyyMMddHHmmss"));

        Spot spot = spotDao.selectById(spotOrder.getSpotId());
        double spotPrice = spot.getPrice();
        spotOrder.setPrice(spotPrice);
        spot.setAmount(spot.getAmount() - 1);
        spotDao.updateById(spot);
        spotOrderDao.insert(spotOrder);


    }

    public List<SpotOrder> selectByUserId(Integer userId) {
        SpotOrder spotOrder = new SpotOrder();
        spotOrder.setUserId(userId);
        return spotOrderDao.selectAll(spotOrder);
    }

    /* 分页查询 */
    public PageInfo<SpotOrder> selectPage(SpotOrder spotOrder, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<SpotOrder> list = spotOrderDao.selectAll(spotOrder);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id) {
        spotOrderDao.deleteById(id);
    }

    public void deleteAndIncreaseAmount(Integer id) {
        SpotOrder spotOrder = spotOrderDao.selectById(id);

        Spot spot = spotDao.selectById(spotOrder.getSpotId());
        spot.setAmount(spot.getAmount() + 1);
        spotDao.updateById(spot);

        spotOrderDao.deleteById(id);
    }

    public void pay(Integer id) {
        SpotOrder spotOrder = spotOrderDao.selectById(id);
        spotOrder.setStatus(OrderEnum.STATUS_PAID.status);
        spotOrderDao.updateById(spotOrder);
    }

    public void finished(Integer id) {
        SpotOrder spotOrder = spotOrderDao.selectById(id);
        spotOrder.setStatus(OrderEnum.STATUS_FINISHED.status);
        spotOrderDao.updateById(spotOrder);
    }
}
