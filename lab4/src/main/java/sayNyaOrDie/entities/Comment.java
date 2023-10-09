package sayNyaOrDie.entities;

import sayNyaOrDie.exceptions.CommentsExceptions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    @Getter
    @Setter
    private long id;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "comment_task")
    private Task task;
    @Column(name = "comment_content")
    @Getter
    @Setter
    private String content;
    @Column(name = "comment_author")
    @Getter
    @Setter
    private String author;
    @Column(name = "comment_date")
    @Getter
    @Setter
    private Date creationDate;

    public Comment(long id, long taskId, String content, String author, Date creationTime) throws CommentsExceptions {
        if (content == null || author == null || creationTime == null) {
            throw new CommentsExceptions("invalid Type input");
        }

        this.id = id;
        this.task.setId(taskId);
        this.content = content;
        this.author = author;
        this.creationDate = creationTime;
    }

    public Comment() {

    }
}
