package com.webforest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webforest.entity.EmployeeEntity;
import com.webforest.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class EmpController {
    private final EmployeeService employeeService;

    public EmpController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // Add your controller methods here
    @GetMapping("/")
    public String getMethodName(@RequestParam(value = "param", defaultValue = "World!") String param) {
        return new String("Hello " + param);
    }

    @GetMapping("/employees")
    public List<EmployeeEntity> getEmployees() {
        
        return employeeService.getEmployees();
    }
    
    @GetMapping("/employees/{id}")
    public EmployeeEntity getEmployeeById(@PathVariable Long id) {
        // Implement logic to retrieve employee by ID
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/employees")
    public String addEmployee(@RequestBody EmployeeEntity emp) {
        try{
            // Validate request body
            if (emp == null) {
                throw new IllegalArgumentException("Request body cannot be null");
            }
            if (emp.getName() == null || emp.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Employee name is required and cannot be empty");
            }
            if (emp.getAge() == null || emp.getAge() <= 0) {
                throw new IllegalArgumentException("Employee age is required and must be greater than 0");
            }
            if (emp.getDepartment() == null || emp.getDepartment().trim().isEmpty()) {
                throw new IllegalArgumentException("Employee department is required and cannot be empty");
            }
            
            // Ensure ID is null for new employees
            emp.setId(null);
            emp.setVersion(null);
            
            System.out.println("Received employee: " + emp.getName() + ", " + emp.getAge() + ", " + emp.getDepartment());
            
            return employeeService.addEmployee(emp);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return "Validation failed: " + e.getMessage();
        } catch (Exception e) {
            System.out.println("Error parsing employee data: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
   
    @PutMapping("/employees/{id}")
    public String updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity emp) {
        return employeeService.updateEmployee(id, emp);
    }

    @DeleteMapping("/employees")
    public String deleteEmployee(@RequestParam Long id) {
        return employeeService.deleteEmployee(id);
    }
}
