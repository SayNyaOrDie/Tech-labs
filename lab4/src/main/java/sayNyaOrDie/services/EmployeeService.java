package sayNyaOrDie.services;

import sayNyaOrDie.entities.Employee;
import sayNyaOrDie.exceptions.EmployeesExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sayNyaOrDie.repositories.EmployeeRepository;

import java.util.Optional;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee update(long id, Employee employee) throws EmployeesExceptions {
        var optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeesExceptions("Employee with employee_id:" + id + " not found");
        } 

        optionalEmployee.get().setDateOfBirth(employee.getDateOfBirth());
        optionalEmployee.get().setName(employee.getName());
        optionalEmployee.get().setAdmin(employee.isAdmin());
        optionalEmployee.get().setHashedPassword(passwordEncoder.encode(employee.getHashedPassword()));

        return employeeRepository.save(optionalEmployee.get());
    }

    public Employee updateSelf(Employee employee, Employee currentUser) throws EmployeesExceptions {
        if (!currentUser.isAdmin() && employee.isAdmin()) {
            throw new EmployeesExceptions("User cannot make himself an admin");
        }
        currentUser.setDateOfBirth(employee.getDateOfBirth());
        currentUser.setName(employee.getName());
        currentUser.setAdmin(employee.isAdmin());
        // пароль обязателен в RequestBody и указывается через hashedPassword
        currentUser.setHashedPassword(passwordEncoder.encode(employee.getHashedPassword()));

        return employeeRepository.save(currentUser);
    }

    public List<Employee> findAll()
    {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id){
        return employeeRepository.findById(id);
    }


    public void delete(long id){
        employeeRepository.deleteById(id);
    }

}
