package sayNyaOrDie.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sayNyaOrDie.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
