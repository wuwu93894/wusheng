package com.example.demo.service;

import java.util.ArrayList;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.bean.LoginUser;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {
	//注入 userMapper
	@Resource
	private UserMapper userMapper;
	//注入 MessageSource 内置类
	@Autowired
	private MessageSource messageSource;  //messageSouece 资源文件 国际化用
            // Locale 是根据浏览器语言设定 优先 使用浏览器用的语言
	// private Locale locale=Locale.getDefault();
	public ArrayList<String> getResult(UserForm userForm, Locale locale) {
		// 获取 LoginUser的 数据库数据  通过 userMapper的find方法
		LoginUser user = userMapper.find(userForm.getAccountId());
		//创建集合 errorlist 
		ArrayList<String> errorlist = new ArrayList<String>();
		if (user == null) {
			 errorlist.add(messageSource.getMessage("login.message.accountId.error",
			 null,locale));
		} else if (!user.getPassword().equals(userForm.getPassword())) {
			 errorlist.add(messageSource.getMessage("login.message.password.error", null,
			 locale));
		}
		return errorlist;
	}
	
}