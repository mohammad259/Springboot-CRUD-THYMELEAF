package com.mohammad.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mohammad.springboot.model.Employee;
import com.mohammad.springboot.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@GetMapping("/")
	public String viewHomePage(Model model) {

		return findPaginated(1,"firstname","asc", model);
	}

	@GetMapping("/showNewEmployee")
	public String showNewEmployee(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "addEmployee";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		service.saveEmployee(employee);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		// get employee from the service
		Employee employee = service.getEmployeeById(id);

		// set employee as a model attribute to Pre Populate the form
		model.addAttribute("employee", employee);
		return "updateEmployee";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long id) {

		// call the delete employee method
		service.deleteEmployeeById(id);
		return "redirect:/";
	}

	// /page/1?sortField=name&sortDirectional=asc

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDirect") String sortDirect,
			Model model) {

		int pageSize = 5;

		Page<Employee> page = service.findPaginated(pageNo, pageSize, sortField, sortDirect);
		List<Employee> listOfEmployees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());


		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirect", sortDirect);
		model.addAttribute("reverseSortDirect", sortDirect.equals("asc")? "desc" : "asc");
		
		model.addAttribute("listOfEmployees", listOfEmployees);

		return "index";

	}

}
