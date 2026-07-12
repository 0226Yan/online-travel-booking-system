package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.PasswordChange;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/adminInfo")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Object object = null;
        if(admin.getRole().equals("管理员")){
            object= adminService.login(admin);
        }else if(admin.getRole().equals("用户")){
            User user = new User();
            user.setUsername(admin.getUsername());
            user.setPassword(admin.getPassword());
            object= userService.login(user);
        }
        return Result.success(object);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Admin admin) {
        if(admin.getAdminId() == null) {
            adminService.add(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll(){
        List<Admin> list = adminService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("拦截器已放行，正式调用接口内部，查询管理员信息");
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{adminId}")
    public Result delete(@PathVariable Integer adminId) {
        adminService.delete(adminId);
        return Result.success();
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody PasswordChange request) {
        Integer adminId = request.getAdminId();
        String newPassword = request.getNewPassword();

        // 根据 adminId 获取用户信息
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            return Result.error("用户不存在");
        }

        // 更新用户密码
        admin.setPassword(newPassword);
        adminService.update(admin);

        return Result.success();
    }
}
