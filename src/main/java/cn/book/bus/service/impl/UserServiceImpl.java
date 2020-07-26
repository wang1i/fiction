package cn.book.bus.service.impl;

import cn.book.bus.domain.User;
import cn.book.bus.mapper.UserMapper;
import cn.book.bus.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean verificationUser(String username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user=userMapper.selectOne(queryWrapper);
        if (null!=user){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean verificationUser(String username, String password) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user=userMapper.selectOne(queryWrapper);
        if (null!=user){
            return true;
        }else {
            return false;
        }

    }
}
