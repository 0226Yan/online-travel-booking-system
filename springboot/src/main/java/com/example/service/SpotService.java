package com.example.service;

import com.example.dao.SpotDao;
import com.example.entity.Spot;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpotService {

    @Resource
    private SpotDao spotDao;

    public PageInfo<Spot> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<Spot> list = spotDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Spot spot) {
        spotDao.insertSelective(spot);
    }

    public void update(Spot spot) {
        spotDao.updateByPrimaryKeySelective(spot);
    }

    public void delete(Integer spotId) {
        spotDao.deleteByPrimaryKey(spotId);
    }

    public List<Spot> selectAll() {
        return spotDao.findAll();
    }

    public Spot selectById(Integer spotId) {
        return spotDao.selectById(spotId);
    }
}
