package com.mightyjava.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mightyjava.modal.Employee;
import com.mightyjava.service.EmployeeService;

@Controller
public class EmployeeController {
	
	private String message;
	
	private Employee employeeObject;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String add(Model model) {
		if(employeeObject == null) {
			employeeObject = new Employee();
		}
		model.addAttribute("message", message);
		model.addAttribute("employee", employeeObject);
		return "add";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("employee") Employee employee, HttpServletRequest request) {
		employeeObject = employee;
		if(employee.getCaptcha().equals(request.getSession().getAttribute("captcha"))) {
			employeeService.add(employeeObject);
			return "redirect:/list";
		} else {
			employeeObject.setCaptcha(null);
			message = "Please verify captcha";
			return "redirect:/";
		}
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		message = null;
		employeeObject = null;
		model.addAttribute("employees", employeeService.employees());
		return "list";
	}
}
