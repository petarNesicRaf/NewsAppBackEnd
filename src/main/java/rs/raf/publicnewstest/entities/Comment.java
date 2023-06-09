package rs.raf.publicnewstest.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    private int id;
    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull(message = "Comment is required")
    @NotEmpty(message = "Comment is required")
    private String comment;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String news_title;

    public Comment() {
    }

    public Comment(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public Comment(int id, String author, String comment) {
        this.id = id;
        this.author = author;
        this.comment = comment;
    }

    public Comment(int id, String author, String comment, String news_title) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.news_title = news_title;
    }

    public Comment(String author, String comment, String news_title) {
        this.author = author;
        this.comment = comment;
        this.news_title = news_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }
}
