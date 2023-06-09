package rs.raf.publicnewstest.repository.category;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.repository.MySqlAbstractRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepo extends MySqlAbstractRepo implements CategoryRepo {

    private String foundName = null;
    @Override
    public List<Category> getAllCategories() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Category> allCategories = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM categories");
            while(resultSet.next())
            {
                allCategories.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCategories;
    }

    @Override
    public Category createCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO categories (name, description) VALUES (?, ?)"
                    ,generatedColumns
            );
            preparedStatement.setString(1, category.getName().toUpperCase().trim());
            preparedStatement.setString(2, category.getDescription().trim());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
            {
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public boolean editCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE categories SET name = ?, description = ? WHERE name = ?"
            );
            preparedStatement.setString(1,category.getName().toUpperCase());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.setString(3, this.foundName);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }

    @Override
    public Category findCategory(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM categories WHERE name = ?");

            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                this.foundName = name;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean deleteCategory(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE category = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.isBeforeFirst())
                return false;

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM categories WHERE name = ?"
            );
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
