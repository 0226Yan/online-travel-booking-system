package com.example.service;

import com.example.common.JwtTokenUtils;
import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll(){
        return adminDao.selectAll(); //请求所有信息
    }

    public PageInfo<Admin> findBySearch(Params params) {   //查询键
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        // 1. 判断用户名是否为空
        if (admin.getUsername() == null || "".equals(admin.getUsername())) {
            throw new CustomException("用户名不能为空");
        }

        // 2. 判断管理员用户名是否重复
        Admin user = adminDao.findByName(admin.getUsername());
        if (user != null) {
            throw new CustomException("该用户名已存在，请更换用户名");
        }

        // 初始化密码
        if (admin.getPassword() == null) {
            admin.setPassword("123");
        }

        // 初始化管理员头像：如果没有上传头像，则根据性别设置默认头像
        if (admin.getImg() == null || admin.getImg().isEmpty()) {
            if ("男".equals(admin.getGender())) {
                admin.setImg("1712086489328");  // 男管理员默认头像
            } else if ("女".equals(admin.getGender())) {
                admin.setImg("1711886704203");  // 女管理员默认头像
            } else {
                admin.setImg("1711886704203");  // 没选性别时默认男头像
            }
        }

        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer adminId) {
        adminDao.deleteByPrimaryKey(adminId);
    }

    public Admin login(Admin admin) {
        // 1.进行非空判断
        if(admin.getUsername() == null || "".equals(admin.getUsername())){
            throw new CustomException("用户名不能为空");
        }
        if(admin.getPassword() == null || "".equals(admin.getPassword())){
            throw new CustomException("密码不能为空");
        }
        // 2.从数据库中查询相关信息并核对
        Admin user = adminDao.findByNameAndPassword(admin.getUsername(),admin.getPassword());
        if(user == null) {
            throw new CustomException("用户名或密码输入错误");
        }
        //生成该登录用户对应的token, 然后跟着user一起返回前台
        String token = JwtTokenUtils.genToken(user.getAdminId().toString(),user.getPassword());
        user.setToken(token);
        return user;
    }

    public Admin findById(Integer adminId) {
        return adminDao.selectByPrimaryKey(adminId);
    }
}
