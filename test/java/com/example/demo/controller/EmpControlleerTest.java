package com.example.demo.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

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

import com.example.demo.LoginSpringBootApplicationTests;

class EmpControlleerTest extends LoginSpringBootApplicationTests{
	@Autowired
	@InjectMocks
	private EmpListController empList;
	@Autowired
//	private MessageSource messageSource;
//	private Locale locale=Locale.ENGLISH;
	MockMvc mockMvc;
	@BeforeEach
	public void setUp() {
		//构建方法 mockMvc 用来获取 login控制器的数据
		mockMvc=MockMvcBuilders.standaloneSetup(empList).build();
	}
	//测试存在的关键字
	@Test
	void ControllertestHas() throws Exception{
		 MockHttpServletRequestBuilder getRequest=MockMvcRequestBuilders.post("/searchEmp").param("keyWord", "山田");
		 ResultActions results=mockMvc.perform(getRequest);
			results.andDo(print());
			//期待 画面跳转的页面 
			results.andExpect(view().name("empList"));
			results.andExpect(model().errorCount(0));
	}
   //测试不存在的关键字
	@Test
	void ControllertestNot()throws Exception{
		 MockHttpServletRequestBuilder getRequest=MockMvcRequestBuilders.post("/searchEmp").param("keyWord", "刘帅");
		 ResultActions results=mockMvc.perform(getRequest);
			results.andDo(print());
			//期待 画面跳转的页面 
	}


}
