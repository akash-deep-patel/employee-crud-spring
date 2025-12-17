package com.webforest.webforest.services;

import java.util.List;

import com.webforest.Entity.EmployeeEntity;

public interface EmployeeService {
    String addEmployee(EmployeeEntity emp);
    List<EmployeeEntity> getEmployees();
    String updateEmployee(Long id, EmployeeEntity emp);
    String deleteEmployee(Long id);
    EmployeeEntity getEmployeeById(Long id);
}
