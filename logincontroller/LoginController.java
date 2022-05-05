package com.example.demo.logincontroller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserServiceImpl;

@Controller
@ComponentScan({ "service" })
public class LoginController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Autowired
	HttpSession session;

	@GetMapping("/login")
	public String login(@ModelAttribute("form") UserForm form, Model model) {
		return "login";
	}

	@PostMapping("/auth")
	public String auth(@ModelAttribute("form") @Valid UserForm userForm, BindingResult relust, Model model,
			Locale locale) {
		String url = null;
		if (relust.hasErrors()) {
			// ObjectError エラーメッセージ
			List<ObjectError> errorList = relust.getAllErrors();
			model.addAttribute("errorList", errorList);
			url = "/login";
			return url;
		}
		
		List<String> errorList = userServiceImpl.getResult(userForm, locale);
		if (!(errorList.size() == 0)) {
			model.addAttribute("message", errorList.get(0));
			url = "/login";
			return url;
		} else {
			// redirect:/empList　画面遷移
			session.setAttribute("accountId", userForm.getAccountId());
			session.setMaxInactiveInterval(20);
			url = "redirect:/empList";
			return url;
		}

	}
}
