package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.bean.LoginUser;
import com.example.demo.form.UserForm;
//创建一个带参无值的方法接口 通过 imp类的引用实现数据库 服务器 控制器的数据链接
public interface RegisterService {
    public ArrayList<String>insertUser(UserForm userForm);
    public LoginUser getUser(String accountId);
}
