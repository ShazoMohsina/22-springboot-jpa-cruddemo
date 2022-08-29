package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	
	private EmployeeService employeeService;
	
	//(use constructor injection)
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService)
	{
		employeeService = theEmployeeService;
	}
	
	// expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll()
	{
		return employeeService.findAll();
	}
	
	// expose "/employees/{employeeId} and return the employee by id
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId)
	{
		Employee employee = employeeService.findById(employeeId);
		
		if(employee == null)
		{
			throw new RuntimeException("Employee Id "+employeeId+" not found");
		}
		return employee;
	}
	
	// add mapping for POST "/employees" , add new employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee)
	{
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	// add mapping for PUT "/employees/{employeeId}" , update an existing employee
	
	@PutMapping("/employees")
	public Employee updatEmployee(@RequestBody Employee employee)
	{
		employeeService.save(employee);
		
		return employee;
	}
	
	// add mapping for DELETE "/employees/{employeeId}", delete an existing employee
	@DeleteMapping("/employees/{employeeId}")
	public String  deleteEmployee(@PathVariable int employeeId)
	{
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		// throw exception if null
		
		if(tempEmployee == null)
		{
			throw new RuntimeException("Employee Id : "+employeeId+" not found");
			
		}
		else {
			employeeService.deleteById(employeeId);
		}
		return "Deleted Employee Id : "+employeeId;
		
	}
	
	 // add mapping for GET "/employees/{employeeName}" 
//	@GetMapping("/employees/{employeeName}")
//	public List<Employee> getEmployeebyName(@PathVariable String employeeName)
//	{
//		
//		List<Employee> employees = employeeService.findByName(employeeName);
//		
//		if(employees == null)
//		{
//			throw new RuntimeException("Employee Name : "+employeeName+" not found");
//		}
//		
//		return employees;
//	}
}









