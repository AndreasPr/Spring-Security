package com.spring.security.controller;

import com.spring.security.domain.Employee;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/employees")
public class EmployeeControllerManagement {

    private static final List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1, "Employee 1"),
            new Employee(2, "Employee 2"),
            new Employee(3, "Employee 3"),
            new Employee(4, "Employee 4")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN_ROLE','SUPERVISOR_ROLE')")
    public List<Employee> getEmployees(){
        return EMPLOYEES;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('employee:write')")
    public void registerEmployee(@RequestBody Employee employee){
        System.out.println("We register our new Employee: " + employee);
    }

    @PutMapping(path = "{employeeId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public void updateExistingEmployee(@PathVariable("employeeId") Integer employeeId, @RequestBody Employee employee){
        System.out.println(String.format("%s %s", employeeId, employee));
    }

    @DeleteMapping(path = "{employeeId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public void deleteExistingEmployee(@PathVariable("employeeId") Integer employeeId){
        System.out.println("We delete an existing employee: " + employeeId);
    }

}
