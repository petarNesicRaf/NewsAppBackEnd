package rs.raf.publicnewstest.repository.comment;

import rs.raf.publicnewstest.entities.Comment;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.repository.MySqlAbstractRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySqlCommentRepo extends MySqlAbstractRepo implements CommentRepo {
    @Override
    public List<Comment> getCommentsByNews(String title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> allComments = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM comments WHERE news_title = ? ");

            preparedStatement.setString(1,title);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allComments.add( new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getString("comment"),
                        resultSet.getString("news_title")

                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allComments;
    }

    @Override
    public Comment createComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO comments (author, comment, news_title) VALUES (?,?,?)"
                    , generatedColumns
            );
            preparedStatement.setString(1, comment.getAuthor().trim());
            preparedStatement.setString(2, comment.getComment().trim());
            preparedStatement.setString(3, comment.getNews_title().trim());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }
}
