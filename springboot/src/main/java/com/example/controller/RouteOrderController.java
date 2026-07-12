package com.example.controller;

import com.example.common.Result;
import com.example.entity.RouteOrder;
import com.example.service.RouteOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/routeOrder")
public class RouteOrderController {

    @Resource
    private RouteOrderService routeOrderService;

    @PostMapping("/add")
    public Result add(@RequestBody RouteOrder routeOrder) {
        routeOrderService.add(routeOrder);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Integer userId) {
        List<RouteOrder> routeOrder = routeOrderService.selectByUserId(userId);
        return Result.success(routeOrder);
    }

    /* 分页查询 */
    @GetMapping("/selectPage")
    public Result selectPage(RouteOrder routeOrder,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RouteOrder> page = routeOrderService.selectPage(routeOrder, pageNum, pageSize);
        return Result.success(page);
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        routeOrderService.deleteById(id);
        return Result.success();
    }

    //支付
    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable Integer id) {
        routeOrderService.pay(id);
        return Result.success();
    }

    //订单完成
    @GetMapping("/finished/{id}")
    public Result finished(@PathVariable Integer id) {
        routeOrderService.finished(id);
        return Result.success();
    }
}
