package com.nikhil.ems.service;

import com.nikhil.ems.exception.DuplicateEmailException;
import com.nikhil.ems.exception.ResourceNotFoundException;
import com.nikhil.ems.model.Employee;
import com.nikhil.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll(String department) {
        if (department != null && !department.isBlank()) {
            return repository.findByDepartmentIgnoreCase(department);
        }
        return repository.findAll();
    }

    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee create(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + employee.getEmail());
        }
        employee.setId(null);
        return repository.save(employee);
    }

    public Employee update(Long id, Employee updated) {
        Employee existing = getById(id);
        if (repository.existsByEmailAndIdNot(updated.getEmail(), id)) {
            throw new DuplicateEmailException("Email already exists: " + updated.getEmail());
        }
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        existing.setJobTitle(updated.getJobTitle());
        existing.setSalary(updated.getSalary());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Employee existing = getById(id);
        repository.delete(existing);
    }
}
