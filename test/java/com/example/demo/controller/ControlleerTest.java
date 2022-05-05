package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.ObjectError;

import com.example.demo.LoginSpringBootApplicationTests;
import com.example.demo.logincontroller.LoginController;

class ControlleerTest extends LoginSpringBootApplicationTests {
	@Autowired
	@InjectMocks
	private LoginController login;
	@Autowired
	private MessageSource messageSource;
	private Locale locale = Locale.ENGLISH;
	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		// 构建方法 mockMvc 用来获取 login控制器的数据
		mockMvc = MockMvcBuilders.standaloneSetup(login).build();
	}

	// 测试用户名 密码正确 登录成功
	@Test
	public void controllertest() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth")
				.param("accountId", "111@elife.com").param("password", "000001");

		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		//画面跳转的页面
		results.andExpect(view().name("redirect:/empList"));
		results.andExpect(model().errorCount(0));
	}

	// 用户名为空的测试
	@Test
	public void controllertestAccountIdEmpty() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth").param("accountId", "")
				.param("password", "000001");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		// 期待 画面跳转的页面
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(1));
		// 获取错误
		@SuppressWarnings("unchecked")
		List<ObjectError> erroeList = (List<ObjectError>) results.andReturn().getModelAndView().getModel()
				.get("errorList");
		// 获取错误信息
		String message = erroeList.get(0).getDefaultMessage();
		Assertions.assertTrue(message.contains("login.error.accountId.notEmpty"));
	}

	// 测试密码为空
	@Test
	public void controllertestPassNotEmpty() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth")
				.param("accountId", "111@abcdef.com").param("password", "");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		// 期待 画面跳转的页面
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(2));
		// 获取错误
		@SuppressWarnings("unchecked")
		List<ObjectError> erroeList = (List<ObjectError>) results.andReturn().getModelAndView().getModel()
				.get("errorList");
		// 获取错误信息
		List<String> messages = new ArrayList<>();
		// 获取错误信息 传值给messages集合
		for (ObjectError error : erroeList) {
			String message = error.getDefaultMessage();
			messages.add(message);
		}
		Assertions.assertTrue(messages.contains("{login.error.password.notEmpty}"));
	}

	@Test
	// 测试 用户名不是email
	public void controllertestNotEmail() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth").param("accountId", "13245647")
				.param("password", "000001");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		// 期待 画面跳转的页面
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(1));
		// 获取错误
		@SuppressWarnings("unchecked")
		List<ObjectError> erroeList = (List<ObjectError>) results.andReturn().getModelAndView().getModel()
				.get("errorList");
		// 获取错误信息
		String message = erroeList.get(0).getDefaultMessage();
		Assertions.assertTrue(message.contains("login.error.accountId.isEmail"));
	}

	// 测试密码不是6位
	@Test
	public void controllerTestPassLength() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth")
				.param("accountId", "111@elife.com").param("password", "001");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		// 期待 画面跳转的页面
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(1));
		// 获取错误
		@SuppressWarnings("unchecked")
		List<ObjectError> erroeList = (List<ObjectError>) results.andReturn().getModelAndView().getModel()
				.get("errorList");
		// 获取错误信息
		String message = erroeList.get(0).getDefaultMessage();
		Assertions.assertTrue(message.contains("login.error.password.length"));
	}

	@Test
	// 测试 用户名不存在
	public void controllertestAccountId() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth")
				.param("accountId", "111@asas.com").param("password", "000001");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(0));
		String message = (String) results.andReturn().getModelAndView().getModel().get("message");
		// 获取错误信息
		Assertions.assertEquals(messageSource.getMessage("login.message.accountId.error", null, locale), message);
	}

	// 测试用户名存在 密码不对
	@Test
	public void controllertestPassword() throws Exception {
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/auth")
				.param("accountId", "111@elife.com").param("password", "000002");
		ResultActions results = mockMvc.perform(getRequest);
		results.andDo(print());
		results.andExpect(view().name("login"));
		results.andExpect(model().errorCount(0));
		String message = (String) results.andReturn().getModelAndView().getModel().get("message");
		Assertions.assertEquals(messageSource.getMessage("login.message.password.error", null, locale), message);
	}
}
