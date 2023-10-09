package sayNyaOrDie.entities;

import sayNyaOrDie.exceptions.TasksExceptions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    @Getter
    @Setter
    private long id;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "task_employee")
    private Employee employee;
    @Column(name = "task_name")
    @Getter
    @Setter
    private String name;
    @Column(name = "task_deadline")
    @Getter
    @Setter
    private Date deadline;
    @Column(name = "task_description")
    @Getter
    @Setter
    private String description;
    @Column(name = "task_type")
    @Getter
    @Setter
    private String type;
    @Column(name = "task_author")
    @Getter
    @Setter
    private String author;

    public Task(long id, long employeeId, String name, Date deadline, String description, String type, String author) throws TasksExceptions {
        if (name == null || deadline == null || description == null || type == null || author == null) {
            throw new TasksExceptions("invalid Type input");
        }
        this.id = id;
        this.employee.setId(employeeId);
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.type = type;
        this.author = author;
    }

    public Task() {

    }

}
