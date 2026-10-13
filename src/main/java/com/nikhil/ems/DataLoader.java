package com.nikhil.ems;

import com.nikhil.ems.model.Employee;
import com.nikhil.ems.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seedData(EmployeeRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Employee("Aarav", "Sharma", "aarav.sharma@example.com", "Engineering", "Software Engineer", new BigDecimal("600000")),
                        new Employee("Priya", "Verma", "priya.verma@example.com", "HR", "HR Executive", new BigDecimal("450000")),
                        new Employee("Rohan", "Mehta", "rohan.mehta@example.com", "Engineering", "Backend Developer", new BigDecimal("700000"))
                ));
            }
        };
    }
}
