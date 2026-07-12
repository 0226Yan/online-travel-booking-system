package com.example.controller;


import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Flight;
import com.example.entity.Params;
import com.example.entity.Spot;
import com.example.service.FlightService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/flightInfo")
public class FlightController {

    @Resource
    private FlightService flightService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Flight> info = flightService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Flight flight) {
        if(flight.getFlightId() == null) {
            flightService.add(flight);
        } else {
            flightService.update(flight);
        }
        return Result.success();
    }

    @DeleteMapping("/{flightId}")
    public Result delete(@PathVariable Integer flightId) {
        flightService.delete(flightId);
        return Result.success();
    }

    /* 渲染数据到用户预订页面 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Flight> list = flightService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer flightId) {
        Flight flight = flightService.selectById(flightId);
        return Result.success(flight);
    }
}
