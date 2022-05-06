package com.example.demo.service;

import java.util.ArrayList;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.bean.LoginUser;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UserMapper;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
	//注入 userMapper
	@Resource
	private UserMapper userMapper;
	//注入 MessageSource 内置类
            // Locale 是根据浏览器语言设定 优先 使用浏览器用的语言
	// private Locale locale=Locale.getDefault();
			@Override
			public ArrayList<String> insertUser(UserForm userForm) {
	        return userMapper.insertUser( userForm);
			}
			@Override
			public LoginUser getUser(String accountId) {
				return userMapper.getUser(accountId);
			}

}