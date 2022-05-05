package com.example.demo.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bean.LoginUser;
import com.example.demo.form.UserForm;

@Mapper
public interface UserMapper {
	// find方法获取数据库信息
	public LoginUser find(String accountId);

	// 账号注册
	public ArrayList<String> insertUser(UserForm userForm);

	public LoginUser getUser(String accountId);

}