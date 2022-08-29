package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Employee> findAll() {
		
		// create a query
		Query theQuery = entityManager.createQuery("from Employee");
		
		// execute query and get result
		List<Employee> employees = theQuery.getResultList();
		
		// return the result
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		
		// get employee
		Employee employee = entityManager.find(Employee.class, theId);
		
		// return employee
		return employee;
	}

	@Override
	public void save(Employee theEmployee) {
		
		// save or update the employee
		Employee dbEmployee = entityManager.merge(theEmployee);
		
		// update with id from db ...so we can get generated id for save/insert
		theEmployee.setId(dbEmployee.getId());

	}

	@Override
	public void deleteById(int theId) {
		
		// delete object with primary key
		Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
		
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();

	}

	@Override
	public List<Employee> findByName(String employeeName) {
		
		Query query = entityManager.createQuery("from Employee where lower(firstName) LIKE :empName or lower(lastName) LIKE :empName");
		
		query.setParameter("empName", "%"+employeeName+"%");
		
		List<Employee> employees = query.getResultList();
		
		return employees;
	}

}
