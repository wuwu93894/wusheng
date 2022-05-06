package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.bean.EmpData;
import com.example.demo.bean.Gender;
import com.example.demo.bean.Nationality;
import com.example.demo.form.EmpForm;
import com.example.demo.service.EmpService;
import com.example.demo.service.GenderService;
import com.example.demo.service.NationalityService;

@Controller
public class AddController {

	@Autowired
	public GenderService genderService;
	@Autowired
	public NationalityService nationalityService;
	@Autowired
	public EmpService empService;
	@Autowired
	public MessageSource messageSource;
	@Autowired
	HttpSession session;

	// 迁移至新規登録画面
	@GetMapping(value = "/toAddEmp")
	public String toAddEmp(@ModelAttribute("form") EmpForm form, Model model) {
		// 取得Gender数据
		ArrayList<Gender> genderList = genderService.listGender();
		// Gender数据向session导入
		session.setAttribute("genderList", genderList);
		// 取得Nationality数据
		ArrayList<Nationality> nationalityList = nationalityService.listNationality();
		// Nationality数据向session导入
		session.setAttribute("nationalityList", nationalityList);
		return "addEmp";
	}

	@PostMapping("/addEmp")
	public String addEmp(@ModelAttribute("form") @Valid EmpForm empForm, BindingResult result, Model model,
			Locale locale) {
		String url;
		
		if (result.hasErrors()) {
			// 错误的时候
			List<ObjectError> errorList = result.getAllErrors();
			// 传入错误数据，迁移至界面
			model.addAttribute("errorList", errorList);
			url = "addEmp";
		} else {
			// 没错的时候
			EmpData empData = empService.getEmpData(empForm.getEmpCd());
			// 入力empData空白
			if (empData != null) {
				//
				model.addAttribute("message", messageSource.getMessage("addEmp.error", null, locale));
				url = "addEmp";
			} else {
				// 登录正确的信息
				empService.insertEmp(empForm);
				url = "redirect:/empList";
			}
		}
		return url;
	}

}
