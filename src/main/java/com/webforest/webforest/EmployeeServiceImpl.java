package com.webforest.webforest;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String addEmployee(Employee emp) {
        try {
            System.out.println("Adding employee: " + emp.getName() + ", " + emp.getAge() + ", " + emp.getDepartment());
            // Set version to null to ensure it's treated as a new entity
            emp.setVersion(null);
            Employee savedEmployee = employeeRepository.save(emp);
            return "Employee added successfully with ID: " + savedEmployee.getId();
        } catch (ObjectOptimisticLockingFailureException e) {
            System.err.println("Optimistic locking failure: " + e.getMessage());
            return "Failed to add employee - concurrent modification detected";
        } catch (Exception e) {
            System.err.println("Error adding employee: " + e.getMessage());
            throw new RuntimeException("Failed to add employee: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            System.out.println("Retrieving all employees: " + employees.size() + " records");
            return employees;
        } catch (Exception e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve employees: " + e.getMessage(), e);
        }
    }

    @Override
    public String updateEmployee(Employee emp) {
        try {
            if (emp.getId() == null) {
                return "Employee ID is required for update";
            }
            Optional<Employee> existingEmployee = employeeRepository.findById(emp.getId());
            if (existingEmployee.isPresent()) {
                // Preserve the version from the existing employee
                emp.setVersion(existingEmployee.get().getVersion());
                System.out.println("Updating employee with version: " + emp.getVersion());
                employeeRepository.save(emp);
                //check the version of saved employee
                Optional<Employee> updatedEmployee = employeeRepository.findById(emp.getId());
                System.out.println("Employee updated: " + updatedEmployee.get().getVersion());
                return "Employee updated successfully";
            }
            return "Employee not found";
        } catch (ObjectOptimisticLockingFailureException e) {
            System.err.println("Optimistic locking failure during update: " + e.getMessage());
            return "Failed to update employee - concurrent modification detected";
        } catch (Exception e) {
            System.err.println("Error updating employee: " + e.getMessage());
            throw new RuntimeException("Failed to update employee: " + e.getMessage(), e);
        }
    }

    @Override
    public String deleteEmployee(Long id) {
        try {
            if (id == null) {
                return "Employee ID is required for delete";
            }
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                return "Employee deleted successfully";
            }
            return "Employee not found";
        } catch (ObjectOptimisticLockingFailureException e) {
            System.err.println("Optimistic locking failure during delete: " + e.getMessage());
            return "Failed to delete employee - concurrent modification detected";
        } catch (Exception e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            throw new RuntimeException("Failed to delete employee: " + e.getMessage(), e);
        }
    }
}

