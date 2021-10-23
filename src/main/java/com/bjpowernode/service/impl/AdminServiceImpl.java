package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        Admin admin = new Admin();
        admin.setaName(name);
        admin.setaPass(MD5Util.getMD5(pwd));
        System.out.println(adminMapper);
        Integer test = adminMapper.selectByNameAndPwd(admin);
        System.out.println(test);
        return null==test?null:admin;
//        return admin;
    }
}
