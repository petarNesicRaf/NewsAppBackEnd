package rs.raf.publicnewstest.repository.tags;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.entities.Tag;
import rs.raf.publicnewstest.repository.MySqlAbstractRepo;
import rs.raf.publicnewstest.requests.TagRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepo extends MySqlAbstractRepo implements TagRepo{
    @Override
    public List<Tag> getAllTags() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Tag> allTags = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM tags");
            while(resultSet.next())
            {
                allTags.add(new Tag(
                        resultSet.getInt("id"),
                        resultSet.getString("keyword")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allTags;
    }

    @Override
    public Tag createTag(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO tags (keyword) VALUES (?)"
                    ,generatedColumns
            );
            preparedStatement.setString(1, tag.getKeyword().trim());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
            {
                tag.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tag;
    }

    @Override
    public boolean addTagToNews(TagRequest tagRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO news_tags (news_title, tag_keyword) VALUES (?,?)",
                    generatedColumns
            );
            preparedStatement.setString(1,tagRequest.getNews_title());
            preparedStatement.setString(2, tagRequest.getTag_keyword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Tag> getTagsByNews(String newsTitle) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet res = null;
        List<Tag> tags = new ArrayList<>();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news_tags WHERE news_title =?"
            );
            preparedStatement.setString(1,newsTitle);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                String tagKeyword = resultSet.getString(3);
                preparedStatement =connection.prepareStatement(
                        "SELECT * FROM tags WHERE keyword= ?"
                );
                preparedStatement.setString(1,tagKeyword);
                res = preparedStatement.executeQuery();
                if(res.next())
                {
                    tags.add(new Tag(
                            res.getInt(1),
                            res.getString(2)
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    @Override
    public List<News> getNewsByTag(String keyword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet res = null;
        List<News> news = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news_tags WHERE tag_keyword =?"
            );
            preparedStatement.setString(1, keyword);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                String newsTitle = resultSet.getString(2);
                preparedStatement =connection.prepareStatement(
                        "SELECT * FROM news WHERE title= ?"
                );
                preparedStatement.setString(1,newsTitle.trim());

                res = preparedStatement.executeQuery();
                if(res.next())
                {
                    System.out.println("TITTITIT " + res.getString("title"));

                    news.add(new News(
                            res.getInt("id"),
                            res.getString("title"),
                            res.getString("text"),
                            res.getDate("dateTime"),
                            res.getString("author"),
                            res.getString("category")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return news;

    }


}
