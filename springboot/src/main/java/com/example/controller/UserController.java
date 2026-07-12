package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.PasswordChange;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/userInfo")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<User> info = userService.findBySearch(params);
        return Result.success(info);
    }

    @GetMapping
    public Result findAll(){
        List<User> list = userService.findAll();
        return Result.success(list);
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        if(user.getUserId() == null) {
            userService.add(user);
        } else {
            userService.update(user);
        }
        return Result.success();
    }

    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable Integer userId) {
        userService.delete(userId);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.Exists(user.getUsername())) {
            return Result.error("用户名已被注册，请登录或使用其他用户名");
        }
        // 不存在，注册账号
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody PasswordChange request) {
        Integer userId = request.getUserId();
        String newPassword = request.getNewPassword();

        // 根据 userId 获取用户信息
        User user = userService.findById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 更新用户密码
        user.setPassword(newPassword);
        userService.update(user);

        return Result.success();
    }
}
