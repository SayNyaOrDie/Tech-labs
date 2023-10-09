package sayNyaOrDie.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sayNyaOrDie.entities.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    // Нужен для EmployeeUserDetailsService
    Employee findByName(String name);
}
