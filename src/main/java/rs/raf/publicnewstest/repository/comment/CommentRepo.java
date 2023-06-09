package rs.raf.publicnewstest.repository.comment;

import rs.raf.publicnewstest.entities.Comment;

import java.util.List;

public interface CommentRepo {

    List<Comment> getCommentsByNews(String title);

    Comment createComment(Comment comment);

}
