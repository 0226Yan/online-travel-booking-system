package com.example.controller;

import com.example.common.Result;
import com.example.entity.HotelOrder;
import com.example.service.HotelOrderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hotelOrder")
public class HotelOrderController {

    private static final Logger logger = LoggerFactory.getLogger(HotelOrderController.class);

    @Resource
    private HotelOrderService hotelOrderService;

    @PostMapping("/add")
    public Result add(@RequestBody HotelOrder hotelOrder) {
        hotelOrderService.add(hotelOrder);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Integer userId) {
        List<HotelOrder> hotelOrder = hotelOrderService.selectByUserId(userId);
        return Result.success(hotelOrder);
    }

    /* 分页查询 */
    @GetMapping("/selectPage")
    public Result selectPage(HotelOrder hotelOrder,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<HotelOrder> page = hotelOrderService.selectPage(hotelOrder, pageNum, pageSize);
        return Result.success(page);
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        hotelOrderService.deleteById(id);
        return Result.success();
    }

    //退房
    @GetMapping("/finished/{id}")
    public Result finished(@PathVariable Integer id) {
        hotelOrderService.finished(id);
        return Result.success();
    }

    //支付
    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable Integer id) {
        hotelOrderService.pay(id);
        return Result.success();
    }
}
