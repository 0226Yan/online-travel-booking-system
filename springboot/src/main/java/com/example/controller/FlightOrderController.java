package com.example.controller;

import com.example.common.Result;
import com.example.entity.FlightOrder;
import com.example.service.FlightOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/flightOrder")
public class FlightOrderController {

    @Resource
    private FlightOrderService flightOrderService;

    @PostMapping("/add")
    public Result add(@RequestBody FlightOrder flightOrder) {
        flightOrderService.add(flightOrder);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Integer userId) {
        List<FlightOrder> spotOrder = flightOrderService.selectByUserId(userId);
        return Result.success(spotOrder);
    }

    /* 分页查询 */
    @GetMapping("/selectPage")
    public Result selectPage(FlightOrder flightOrder,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<FlightOrder> page = flightOrderService.selectPage(flightOrder, pageNum, pageSize);
        return Result.success(page);
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        flightOrderService.deleteById(id);
        return Result.success();
    }

    //支付
    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable Integer id) {
        flightOrderService.pay(id);
        return Result.success();
    }

    //订单完成
    @GetMapping("/finished/{id}")
    public Result finished(@PathVariable Integer id) {
        flightOrderService.finished(id);
        return Result.success();
    }
}
