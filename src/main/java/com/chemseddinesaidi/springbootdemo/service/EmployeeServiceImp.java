package com.chemseddinesaidi.springbootdemo.service;

import com.chemseddinesaidi.springbootdemo.model.Employee;
import com.chemseddinesaidi.springbootdemo.error.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImp implements EmployeeService{

    List<Employee> employees = new ArrayList<>();
    @Override
    public Employee save(Employee employee) {

        if(employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()){
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId + "."));
    }

    @Override
    public String deleteEmployeeById(String id) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getEmployeeId().equalsIgnoreCase(id))
                .findFirst()
                .get();
        employees.remove(employee);
        return "Employee with id: " + id + " has been deleted.";
    }
}
