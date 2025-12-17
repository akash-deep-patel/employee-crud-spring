package com.webforest.service;

import java.util.List;

import com.webforest.entity.EmployeeEntity;

public interface EmployeeService {
    String addEmployee(EmployeeEntity emp);
    List<EmployeeEntity> getEmployees();
    String updateEmployee(Long id, EmployeeEntity emp);
    String deleteEmployee(Long id);
    EmployeeEntity getEmployeeById(Long id);
}
