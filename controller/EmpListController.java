package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bean.EmpData;
import com.example.demo.service.EmpService;

//控制器注入
@Controller
//
@ComponentScan({ "service" })
public class EmpListController {
	@Autowired
	// 实装类UserService
	private EmpService empService;

	@GetMapping("/empList")
	public String empList(Model model) {
		// serviceList.listEmpメソッドの返却値
		ArrayList<EmpData> empList = empService.listEmp();
		// 社員情報一覧画面のmodelに入れる
		model.addAttribute("emplist", empList);
		session.setAttribute("empList", empList);
		return "empList";
	}

	@PostMapping("/searchEmp")
	// keyWord 关键字检索 社员信息
	public String listEmp(@RequestParam(value = "keyWord") String keyWord, Model model) {

		List<EmpData> empData = empService.searchEmp(keyWord);
		model.addAttribute("emplist", empData);
		session.setAttribute("empList", empData);
		// 转入検索画面
		return "empList";
	}

	// 社員詳細情報画面
	@GetMapping("/showDetails")
	public String showDatails(@RequestParam(value = "empCd") String empCd, Model model) {
		// 社員詳細情報取得
		EmpData empData = empService.getEmpData(empCd);
		model.addAttribute("emp", empData);
		return "empDatails";
	}
	
	@Autowired
	HttpSession session;
	@GetMapping("/empListExcel")
	public void empListExcel(HttpServletResponse response) throws IOException {
		//一覧画面のリストを使う
		@SuppressWarnings("unchecked")
		List<EmpData> empData =  (List<EmpData>) session.getAttribute("empList");
		// excelを生成する
		empService.empListExcel(response, empData);
	} 
}
