package com.example.controller;

import com.example.common.Result;
import com.example.entity.Hotel;
import com.example.entity.Params;
import com.example.entity.Room;
import com.example.service.RoomService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roomInfo")
public class RoomController {

    @Resource
    private RoomService roomService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Room> info = roomService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Room room) {
        if(room.getRoomId() == null) {
            roomService.add(room);
        } else {
            roomService.update(room);
        }
        return Result.success();
    }

    @DeleteMapping("/{roomId}")
    public Result delete(@PathVariable Integer roomId) {
        roomService.delete(roomId);
        return Result.success();
    }

    /* hotel界面、number界面调用 */
    @GetMapping("/selectByHotelId")
    public Result selectByHotelId(@RequestParam Integer hotelId) {
        List<Room> list = roomService.selectByHotelId(hotelId);
        for (Room room : list) {
            room.setRoomImg("http://localhost:8080/api/files/" + room.getRoomImg());
        }
        return Result.success(list);
    }

    /* detail界面调用 */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer roomId) {
        Room room = roomService.selectById(roomId);
        room.setRoomImg("http://localhost:8080/api/files/" + room.getRoomImg());
        return Result.success(room);
    }

}
