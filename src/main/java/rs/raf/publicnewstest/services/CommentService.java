package rs.raf.publicnewstest.services;

import rs.raf.publicnewstest.entities.Comment;
import rs.raf.publicnewstest.repository.comment.CommentRepo;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    CommentRepo commentRepo;

    public List<Comment> getCommentsByNews(String title)
    {
        return this.commentRepo.getCommentsByNews(title);
    }

    public Comment createComment(Comment comment)
    {
        return this.commentRepo.createComment(comment);
    }

}
