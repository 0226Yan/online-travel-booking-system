package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.dao.HotelDao;
import com.example.dao.RoomDao;
import com.example.entity.Hotel;
import com.example.entity.Params;
import com.example.entity.Room;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Resource
    private RoomDao roomDao;

    @Resource
    private HotelDao hotelDao;

    public PageInfo<Room> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<Room> list = roomDao.findBySearch(params);
        if(CollectionUtil.isEmpty(list)){
            return PageInfo.of(new ArrayList<>());
        }
        for (Room room : list) {
            Hotel hotel = hotelDao.selectByPrimaryKey(room.getHotelId());
            room.setHotelName(hotel.getHotelName());
        }
        return PageInfo.of(list);
    }

    public void add(Room room) {
        roomDao.insertSelective(room);
    }

    public void update(Room room) {
        roomDao.updateByPrimaryKeySelective(room);
    }

    public void delete(Integer roomId) {
        roomDao.deleteByPrimaryKey(roomId);
    }

    public List<Room> selectByHotelId(Integer hotelId) {
        return roomDao.selectByHotelId(hotelId);
    }

    public Room selectById(Integer roomId) {
        return roomDao.selectById(roomId);
    }
}
