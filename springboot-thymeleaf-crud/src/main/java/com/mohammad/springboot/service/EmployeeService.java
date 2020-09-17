package com.mohammad.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mohammad.springboot.model.Employee;



public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public void saveEmployee(Employee employee);
	
	public Employee getEmployeeById(long id);

	public void deleteEmployeeById(long id);
	
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	
}
