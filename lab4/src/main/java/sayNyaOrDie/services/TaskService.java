package sayNyaOrDie.services;

import sayNyaOrDie.entities.Employee;
import sayNyaOrDie.entities.Task;
import sayNyaOrDie.exceptions.TasksExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayNyaOrDie.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task save(Task task, Employee employee){
        task.setEmployee(employee);
        return taskRepository.save(task);
    }


    public Task update(long id, Task task) throws TasksExceptions {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()){
            throw new TasksExceptions("Task with task_id:" + id + " not found");
        }

        optionalTask.get().setId(task.getId());
        optionalTask.get().setName(task.getName());
        optionalTask.get().setAuthor(task.getAuthor());
        optionalTask.get().setEmployee(task.getEmployee());
        optionalTask.get().setDeadline(task.getDeadline());
        optionalTask.get().setDescription(task.getDescription());
        optionalTask.get().setType(task.getType());

        return taskRepository.save(optionalTask.get());
    }

    public List<Task> findAll()
    {
        return (List<Task>) taskRepository.findAll();
    }

    public Optional<Task> findById(Long id){
        return taskRepository.findById(id);
    }


    public void delete(long id){
        taskRepository.deleteById(id);
    }

    public void throwIfEmployeeDoesNotOwnTask(long id, Employee employee) throws TasksExceptions {
        var task = taskRepository.findById(id).orElseThrow(() -> new TasksExceptions("Task with task_id: " + id + "not found"));
        if (task.getEmployee().getId() != employee.getId()) {
            throw new TasksExceptions(employee.getName() + " does not own this task");
        }
    }
}
