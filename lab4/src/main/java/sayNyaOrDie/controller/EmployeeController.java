package sayNyaOrDie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sayNyaOrDie.entities.Employee;
import sayNyaOrDie.exceptions.AuthorizationException;
import sayNyaOrDie.exceptions.EmployeesExceptions;
import sayNyaOrDie.services.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/self")
    @PreAuthorize("hasRole('USER')") // с этой ручкой можно проверить с какого акка ты сидишь
    public Employee self(@AuthenticationPrincipal Employee employee) {
        return employee;
    }

    @PostMapping // любой может создать не админа
    public Employee createEmployee(@RequestBody Employee employee) throws AuthorizationException {
        if (employee.isAdmin()) {
            throw new AuthorizationException("Can't create admin user");
        }
        return employeeService.save(employee);
    }

    @PutMapping("/update{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws EmployeesExceptions {
        return employeeService.update(id, employee);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public Employee updateSelf(@RequestBody Employee employee, @AuthenticationPrincipal Employee currentUser) throws EmployeesExceptions {
        return employeeService.updateSelf(employee, currentUser);
    }

    @PreAuthorize("hasRole('ADMIN')") // смотреть всех и удалять юзеров могут только админы
    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get{id}")
    public Employee getEmployeeById(@PathVariable Long id) throws EmployeesExceptions {
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeesExceptions("Employee with employee_id: " + id + "not found");
        }

        return optionalEmployee.get();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
