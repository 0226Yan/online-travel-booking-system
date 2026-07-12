package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.OrderEnum;
import com.example.dao.HotelOrderDao;
import com.example.dao.RoomDao;
import com.example.entity.HotelOrder;
import com.example.entity.Room;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HotelOrderService {

    private static final Logger log = LoggerFactory.getLogger(HotelOrderService.class);

    @Resource
    private HotelOrderDao hotelOrderDao;
    @Resource
    private RoomDao roomDao;

    public void add(HotelOrder hotelOrder) {

        if(ObjectUtil.isEmpty(hotelOrder.getInTime()) || ObjectUtil.isEmpty(hotelOrder.getOutTime())) {
            throw new CustomException("日期不能为空");
        }

        hotelOrder.setStatus(OrderEnum.STATUS_UNPAID.status);
        hotelOrder.setTime(DateUtil.now());
        hotelOrder.setOrderId(DateUtil.format(new Date(),"yyyyMMddHHmmss"));

        try{
            //根据入住时间和离开时间计算天数
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(hotelOrder.getInTime());
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(hotelOrder.getOutTime());
            // 比较两个时间的大小
            int result = start.compareTo(end);
            if (result >= 0) {
                throw new CustomException("您选择的时间不合理");
            }

            Long dayNum = getDayNum(hotelOrder.getInTime(), hotelOrder.getOutTime());
            hotelOrder.setDays(dayNum);

            Room room = roomDao.selectById(hotelOrder.getRoomId());
            hotelOrder.setPrice(room.getPrice() * dayNum);
            room.setVacantNum(room.getVacantNum() - 1);
            hotelOrderDao.insert(hotelOrder);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException("您选择的时间不合理");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long getDayNum(String inTime, String outTime) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date star = dft.parse(inTime);//开始时间
        Date endDay = dft.parse(outTime);//结束时间
        Long starTime = star.getTime();
        Long endTime = endDay.getTime();
        long num = endTime-starTime;//时间戳相差的毫秒数
        return num/24/60/60/1000;
    }

    public List<HotelOrder> selectByUserId(Integer userId) {
        HotelOrder hotelOrder = new HotelOrder();
        hotelOrder.setUserId(userId);
        return hotelOrderDao.selectAll(hotelOrder);
    }

    /* 分页查询 */
    public PageInfo<HotelOrder> selectPage(HotelOrder hotelOrder, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<HotelOrder> list = hotelOrderDao.selectAll(hotelOrder);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id) {
        hotelOrderDao.deleteById(id);
    }

    public void finished(Integer id) {
        HotelOrder hotelOrder = hotelOrderDao.selectById(id);
        Room room = roomDao.selectById(hotelOrder.getRoomId());
        room.setVacantNum(room.getVacantNum() + 1);
        roomDao.updateById(room);
        hotelOrder.setStatus(OrderEnum.STATUS_FINISHED.status);
        hotelOrderDao.updateById(hotelOrder);
    }

    public void pay(Integer id) {
        HotelOrder hotelOrder = hotelOrderDao.selectById(id);
        hotelOrder.setStatus(OrderEnum.STATUS_PAID.status);
        hotelOrderDao.updateById(hotelOrder);
    }
}
