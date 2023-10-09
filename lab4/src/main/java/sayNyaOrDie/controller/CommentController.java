package sayNyaOrDie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sayNyaOrDie.entities.Comment;
import sayNyaOrDie.exceptions.CommentsExceptions;
import sayNyaOrDie.services.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
@PreAuthorize("hasRole('USER')")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @PutMapping("/update{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) throws CommentsExceptions {
        return commentService.update(id, comment);
    }

    @GetMapping("/getAll")
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/get{id}")
    public Comment getCommentById(@PathVariable Long id) throws CommentsExceptions {
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isEmpty()) {
            throw new CommentsExceptions("Comment with comment_id: " + id + "not found");
        }

        return optionalComment.get();
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
