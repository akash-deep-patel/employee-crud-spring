package com.webforest.webforest;

import java.util.List;

public interface EmployeeService {
    String addEmployee(Employee emp);
    List<Employee> getEmployees();
    String updateEmployee(Long id, Employee emp);
    String deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
}
