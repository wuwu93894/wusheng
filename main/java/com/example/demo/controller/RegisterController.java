package com.example.demo.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.bean.LoginUser;
import com.example.demo.form.UserForm;
import com.example.demo.service.RegisterService;

@Controller
@ComponentScan({ "service" })
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	@Autowired
	public MessageSource messageSource;

	@GetMapping("/register")
	// 与前端链接 获取前端请求
	public String login(@ModelAttribute("form") UserForm userform, Model model) {
		return "register";
	}

	@PostMapping("/toLogin")
	public String resgiter(@ModelAttribute("form") UserForm userform, BindingResult relust, Model model,
			Locale locale) {
		String url = null;
		if (relust.hasErrors()) {
			// ObjectError 是错误信息 的数据类型 下面是固定用法
			List<ObjectError> errorList = relust.getAllErrors();
			model.addAttribute("errorList", errorList);
			url = "register";
			return url;
		}
		// 请求信息的长度 不为0 返回错误信息
		LoginUser loginUser = registerService.getUser(userform.getAccountId());
		if (loginUser != null) {
			//
			model.addAttribute("message", messageSource.getMessage("addEmp.error", null, locale));
			url = "addEmp";
		} else {
			
			registerService.insertUser(userform);
			url = "redirect:/empList";
		}
		return url;
	}
}
