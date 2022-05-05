package com.example.demo.UserService;

import java.util.ArrayList;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.demo.LoginSpringBootApplicationTests;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

class UserServiceTest extends LoginSpringBootApplicationTests {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	private Locale locale = Locale.getDefault();

	// 测试 用户名和密码正确
	@Test
	void testGetResult() {
		UserForm user = new UserForm();
		user.setAccountId("111@elife.com");
		user.setPassword("000001");
		ArrayList<String> errors = null;
		errors = userService.getResult(user, locale);
		Assertions.assertEquals(0, errors.size());
	}

	// 测试用户名不正确
	@Test
	void userServiceTest1() {
		UserForm user = new UserForm();
		user.setAccountId("4123456746");
		user.setPassword("000001");
		ArrayList<String> errors = null;
		errors = userService.getResult(user, locale);
		Assertions.assertEquals(messageSource.getMessage("login.message.accountId.error", null, locale), errors.get(0));
	}

	// 测试密码不正确
	@Test
	void userServiceTest2() {
		UserForm user = new UserForm();
		user.setAccountId("111@elife.com");
		user.setPassword("1231321");
		ArrayList<String> errors = null;
		errors = userService.getResult(user, locale);
		Assertions.assertEquals(messageSource.getMessage("login.message.password.error", null, locale), errors.get(0));
	}

}
