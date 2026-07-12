package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Hotel;
import com.example.entity.Params;
import com.example.service.HotelService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hotelInfo")
public class HotelController {

    @Resource
    private HotelService hotelService;

    //关联room
    @GetMapping
    public Result findAll() {
        return Result.success(hotelService.findAll());
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Hotel> info = hotelService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Hotel hotel) {
        if(hotel.getHotelId() == null) {
            hotelService.add(hotel);
        } else {
            hotelService.update(hotel);
        }
        return Result.success();
    }

    @DeleteMapping("/{hotelId}")
    public Result delete(@PathVariable Integer hotelId) {
        hotelService.delete(hotelId);
        return Result.success();
    }

    /* hotel界面调用 */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer hotelId) {
        Hotel hotel = hotelService.selectById(hotelId);
        hotel.setHotelImg("http://localhost:8080/api/files/" + hotel.getHotelImg());
        return Result.success(hotel);
    }


    /* 渲染数据到travel主页面 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Hotel> list = hotelService.selectAll();
        for (Hotel hotel : list) {
            hotel.setHotelImg("http://localhost:8080/api/files/" + hotel.getHotelImg());
        }
        return Result.success(list);
    }
}
