package com.chemseddinesaidi.springbootdemo.service;

import com.chemseddinesaidi.springbootdemo.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String employeeId);
}
