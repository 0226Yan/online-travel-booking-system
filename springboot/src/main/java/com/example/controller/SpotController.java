package com.example.controller;

import com.example.common.Result;
import com.example.entity.Hotel;
import com.example.entity.Params;
import com.example.entity.Spot;
import com.example.service.SpotService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/spotInfo")
public class SpotController {

    @Resource
    private SpotService spotService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Spot> info = spotService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Spot spot) {
        if(spot.getSpotId() == null) {
            spotService.add(spot);
        } else {
            spotService.update(spot);
        }
        return Result.success();
    }

    @DeleteMapping("/{spotId}")
    public Result delete(@PathVariable Integer spotId) {
        spotService.delete(spotId);
        return Result.success();
    }

    /* spot界面调用 */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer spotId) {
        Spot spot = spotService.selectById(spotId);
        spot.setSpotImage("http://localhost:8080/api/files/" + spot.getSpotImage());
        return Result.success(spot);
    }

    /* 渲染数据到travel主页面 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Spot> list = spotService.selectAll();
        for (Spot spot : list) {
            spot.setSpotImage("http://localhost:8080/api/files/" + spot.getSpotImage());
        }
        return Result.success(list);
    }
}
