package com.example.service;

import com.example.common.JwtTokenUtils;
import com.example.dao.UserDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public PageInfo<User> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 自动按照当前开启的分页设置进行查询
        List<User> list = userDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public List<User> findAll() {
        return userDao.selectAll(); //请求所有信息
    }

    // 检测账号是否存在
    public boolean Exists(String username) {
        User user = userDao.findByUsername(username);
        return user != null;
    }

    public void add(User user) {
        // 生成唯一的账号
        String account = generateUniqueAccount();
        user.setAccount(account);
        user.setRole("user");
        // 初始化头像：如果用户没有上传头像，则根据性别设置默认头像
        if (user.getUserImg() == null || user.getUserImg().isEmpty()) {
            if ("男".equals(user.getGender())) {
                user.setUserImg("1712086489328");  // 男用户默认头像
            } else if ("女".equals(user.getGender())) {
                user.setUserImg("1711886704203");  // 女用户默认头像
            } else {
                user.setUserImg("1711886704203");  // 没选性别时，默认女头像
            }
        }
        userDao.insertSelective(user);
    }
    // 生成唯一的账号
    private String generateUniqueAccount() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "USER_" + uuid.substring(0, 8); //
    }

    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    public void delete(Integer userId) {
        userDao.deleteByPrimaryKey(userId);
    }

    public Object login(User user) {
        // 1.进行非空判断
        if(user.getUsername() == null || "".equals(user.getUsername())){
            throw new CustomException("用户名不能为空");
        }
        if(user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空");
        }
        // 2.从数据库中查询相关信息并核对
        User byNameAndPassword = userDao.findByNameAndPassword(user.getUsername(), user.getPassword());
        if(byNameAndPassword == null) {
            throw new CustomException("用户名或密码输入错误");
        }
        //生成该登录用户对应的token, 然后跟着user一起返回前台
        String token = JwtTokenUtils.genToken(byNameAndPassword.getUserId().toString(),byNameAndPassword.getPassword());
        byNameAndPassword.setToken(token);
        return byNameAndPassword;
    }

    public User findById(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

}
