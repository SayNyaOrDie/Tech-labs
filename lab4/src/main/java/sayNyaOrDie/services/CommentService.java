package sayNyaOrDie.services;

import sayNyaOrDie.entities.Comment;
import sayNyaOrDie.exceptions.CommentsExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayNyaOrDie.repositories.CommentRepository;


import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }


    public Comment update(long id, Comment comment) throws CommentsExceptions {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(optionalComment.isEmpty()){
            throw new CommentsExceptions("Comment with comment_id:" + id + " not found");
        }

        optionalComment.get().setId(comment.getId());
        optionalComment.get().setTask(comment.getTask());
        optionalComment.get().setAuthor(comment.getAuthor());
        optionalComment.get().setContent(comment.getContent());
        optionalComment.get().setCreationDate(comment.getCreationDate());

        return commentRepository.save(optionalComment.get());
    }

    public List<Comment> findAll()
    {
        return (List<Comment>) commentRepository.findAll();
    }

    public Optional<Comment> findById(Long id){
        return commentRepository.findById(id);
    }


    public void delete(long id){
        commentRepository.deleteById(id);
    }
}
