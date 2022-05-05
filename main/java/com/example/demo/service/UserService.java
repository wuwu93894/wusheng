package com.example.demo.service;

import java.util.ArrayList;
import java.util.Locale;

import com.example.demo.form.UserForm;
//创建一个带参无值的方法接口 通过 imp类的引用实现数据库 服务器 控制器的数据链接
public interface UserService {
    public ArrayList<String>getResult(UserForm userForm,Locale locale);
}
