package sayNyaOrDie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sayNyaOrDie.entities.Employee;
import sayNyaOrDie.entities.Task;
import sayNyaOrDie.exceptions.TasksExceptions;
import sayNyaOrDie.services.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@PreAuthorize("hasRole('USER')") // реувесты с тасками могут выполнять юзеры
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal Employee employee) {
        return taskService.save(task, employee);
    }

    // @AuthenticationPrincipal привязывает аргумент к текущему юзеру, т.е. в этом методе employee это текущий юзер
    @PutMapping("/update{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @AuthenticationPrincipal Employee employee) throws TasksExceptions {
        // Разрешаем юзеру изменять/удалять/смотреть только свои таски
        taskService.throwIfEmployeeDoesNotOwnTask(id, employee);
        return taskService.update(id, task);
    }

    @GetMapping("/getAll")
    @PostFilter("filterObject.employee.id == principal.id") // Выдаём результат только для текущего юзера (https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-postfilter)
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/get{id}")
    public Task getTaskById(@PathVariable Long id, @AuthenticationPrincipal Employee employee) throws TasksExceptions {
        taskService.throwIfEmployeeDoesNotOwnTask(id, employee);
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()){
            throw new TasksExceptions("Task with task_id: " + id + "not found");
        }

        return optionalTask.get();
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal Employee employee) throws TasksExceptions {
        taskService.throwIfEmployeeDoesNotOwnTask(id, employee);
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
