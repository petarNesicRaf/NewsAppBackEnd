package rs.raf.publicnewstest.repository.news;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.repository.MySqlAbstractRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepo extends MySqlAbstractRepo implements NewsRepo {

    private String foundTitle = null;

    @Override
    public List<News> getAllNews() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news ORDER BY datetime");

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("datetime"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public List<News> getNewsByCategory(String category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE category = ? ORDER BY datetime ");

            preparedStatement.setString(1,category);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("datetime"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }


    @Override
    public News createNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO news (title, text, datetime, author,  category) VALUES (?, ?,CURRENT_DATE, ?,?)"
                    , generatedColumns
            );
            preparedStatement.setString(1, news.getTitle().trim());
            preparedStatement.setString(2, news.getText().trim());
            preparedStatement.setString(3, news.getAuthor().trim());
            preparedStatement.setString(4, news.getCategory().toUpperCase().trim());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return news;

    }

    @Override
    public boolean editNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET title = ?, text = ?,  author = ?,  category = ? WHERE title = ?"
                    ,generatedColumns
            );
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setString(3, news.getAuthor());
            preparedStatement.setString(4, news.getCategory());
            preparedStatement.setString(5, this.foundTitle);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public News findnews(String title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE title = ?");

            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                 news = new News(
                         resultSet.getInt("id"),
                         resultSet.getString("title"),
                         resultSet.getString("text"),
                         resultSet.getDate("datetime"),
                         resultSet.getString("author"),
                         resultSet.getString("category"),
                         resultSet.getInt("readers"));
                this.foundTitle = title;
                preparedStatement = connection.prepareStatement(
                        "UPDATE news SET readers = readers + 1 WHERE title = ?"
                );
                preparedStatement.setString(1, resultSet.getString("title"));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return news;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public boolean deleteNews(String title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection =this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM comments WHERE news_title = ?"
            );
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 0"
            );
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    " DELETE FROM news WHERE title = ?"
            );
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 1"
            );
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<News> getMostRead() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news WHERE dateTime >= CURRENT_DATE - INTERVAL  30 DAY ORDER BY readers DESC LIMIT 10");

            while(resultSet.next())
            {
                    allNews.add(new News(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("text"),
                            resultSet.getDate("datetime"),
                            resultSet.getString("author"),
                            resultSet.getString("category"),
                            resultSet.getInt("readers")
                    ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;

    }
}
