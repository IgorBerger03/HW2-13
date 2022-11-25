package com.example.hw213;

import com.example.hw213.exception.EmployeeNotFoundException;
import com.example.hw213.model.Employee;
import com.example.hw213.service.DepartmentService;
import com.example.hw213.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach()
    public void beforeEach () {
        List<Employee> employees = List.of(
                new Employee("Иван", "Иванов", 2, 50_000),
                new Employee("Александр", "Петров", 1, 55_000),
                new Employee("Екатерина", "Базина", 2, 73_000),
                new Employee("Александра", "Мальцева", 1, 85_000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeMinSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }
    @Test
    public void employeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }
    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeesGroupedByDepartmentPositiveTest(int departmentId, List<Employee> expected) {
        assertThat(departmentService.findEmployeesFromDepartment(departmentId)).containsExactlyElementsOf(expected);
    }
    @Test
    public void employeesGroupedByDepartmentTest() {
        assertThat(departmentService.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Иван", "Иванов", 2, 50_000),
                                new Employee("Александр", "Петров", 1, 55_000)),
                        2,List.of(new  Employee("Екатерина", "Базина", 2, 73_000),
                                new Employee("Александра", "Мальцева", 1, 85_000))
                )
                );
    }
    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of (1,new Employee("Екатерина", "Базина", 2, 73_000)),
                Arguments.of(2, new Employee("Александра", "Мальцева", 1, 85_000))
        );
    }
    public static Stream<Arguments> employeeWithMinSalaryParams(){
        return Stream.of(
                Arguments.of(1, new Employee("Иван", "Иванов", 2, 50_000)),
                Arguments.of(2,new Employee("Александр", "Петров", 1, 55_000))

        );
    }
    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Иван", "Иванов", 2, 50_000),
                        new Employee("Александр", "Петров", 1, 55_000))),
                Arguments.of(2, List.of(new Employee("Екатерина", "Базина", 2, 73_000),
                        new Employee("Александра", "Мальцева", 1, 85_000),
                Arguments.of(3, Collections.emptyList()))));
    }
}
