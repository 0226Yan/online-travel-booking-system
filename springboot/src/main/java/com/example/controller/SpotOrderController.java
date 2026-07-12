package com.example.controller;

import com.example.common.Result;
import com.example.entity.SpotOrder;
import com.example.service.SpotOrderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/spotOrder")
public class SpotOrderController {

    @Resource
    private SpotOrderService spotOrderService;

    @PostMapping("/add")
    public Result add(@RequestBody SpotOrder spotOrder) {
        spotOrderService.add(spotOrder);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Integer userId) {
        List<SpotOrder> spotOrder = spotOrderService.selectByUserId(userId);
        return Result.success(spotOrder);
    }

    /* 分页查询 */
    @GetMapping("/selectPage")
    public Result selectPage(SpotOrder spotOrder,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SpotOrder> page = spotOrderService.selectPage(spotOrder, pageNum, pageSize);
        return Result.success(page);
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        spotOrderService.deleteById(id);
        return Result.success();
    }

    //用户取消订单
    @DeleteMapping("/cancel/{id}")
    public Result deleteAndIncreaseAmount(@PathVariable Integer id) {
        spotOrderService.deleteAndIncreaseAmount(id);
        return Result.success();
    }

    //支付
    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable Integer id) {
        spotOrderService.pay(id);
        return Result.success();
    }

    //订单完成
    @GetMapping("/finished/{id}")
    public Result finished(@PathVariable Integer id) {
        spotOrderService.finished(id);
        return Result.success();
    }
}
