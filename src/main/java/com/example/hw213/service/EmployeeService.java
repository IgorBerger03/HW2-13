package com.example.hw213.service;

import com.example.hw213.exception.EmployeeAlreadyAddedException;
import com.example.hw213.exception.EmployeeNotFoundException;
import com.example.hw213.exception.EmployeeStorageIsFullException;
import com.example.hw213.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    private static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    private final ValidationService validationService;
    public EmployeeService(ValidationService validatorService){
        this.validationService = validatorService;
    }

    private String getKey(String name, String surname){ return name + "|" + surname;}


    public Employee add(String name,
                        String surname,
                        int department,
                        double salary){
        Employee employee = new Employee(validationService.validateName(name),
                validationService.validateSurname(surname),
                department,
                salary);
        String key = getKey( name, surname); {
            if (employees.containsKey(key)) {
                throw new EmployeeAlreadyAddedException("Сотрудник уже есть в списке");
            }
            if (employees.size()< LIMIT){
                employees.put(key, employee);
                return employee;
            }
            throw new EmployeeStorageIsFullException();

        }
    }

    public Employee remove (String name, String surname){
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }
        return employees.get(key);
    }

    public List<Employee> getAll()
    { return new ArrayList<>(employees.values());
    }
}
