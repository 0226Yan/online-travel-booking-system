package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Hotel;
import com.example.entity.Params;
import com.example.entity.Route;
import com.example.service.RouteService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/routeInfo")
public class RouteController {

    @Resource
    private RouteService routeService;

    @PostMapping
    public Result save(@RequestBody Route route) {
        if(route.getTourId() == null) {
            routeService.add(route);
        } else {
            routeService.update(route);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Route> info = routeService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{tourId}")
    public Result delete(@PathVariable Integer tourId) {
        routeService.delete(tourId);
        return Result.success();
    }

    /* spot界面调用 */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer tourId) {
        Route route = routeService.selectById(tourId);
        route.setImg("http://localhost:8080/api/files/" + route.getImg());
        return Result.success(route);
    }

    /* 渲染数据到travel主页面 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Route> list = routeService.selectAll();
        for (Route route : list) {
            route.setImg("http://localhost:8080/api/files/" + route.getImg());
        }
        return Result.success(list);
    }
}
