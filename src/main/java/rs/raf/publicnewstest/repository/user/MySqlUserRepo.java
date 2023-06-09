package rs.raf.publicnewstest.repository.user;

import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.repository.MySqlAbstractRepo;
import rs.raf.publicnewstest.requests.EditUserRequest;
import rs.raf.publicnewstest.requests.LoginRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepo extends MySqlAbstractRepo implements UserRepo {

    private String findEmail;

    @Override
    public User login(LoginRequest loginRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();
            System.out.println("PASSWORD " + loginRequest.getPassword());
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email=? AND password=?"
            );
            preparedStatement.setString(1,loginRequest.getEmail());
            preparedStatement.setString(2,loginRequest.getPassword());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;

    }

    @Override
    public User createUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (email, name, surname, role, status, password) VALUES (?,?,?, ?,?,?)",
                    generatedColumns
            );

            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3,user.getSurname());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setInt(5, 1);
            preparedStatement.setString(6, user.getPassword());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }
            if(resultSet == null)
            {
                System.out.println("NULLLA");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> allUsers = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user");
            while(resultSet.next())
            {
                allUsers.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return allUsers;
    }

    //todo bool
    @Override
    public String editUser(EditUserRequest editUserRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE user SET email = ?, name = ?, surname = ?, role = ? WHERE email = ?"
            );
            preparedStatement.setString(1,editUserRequest.getEmail());
            preparedStatement.setString(2,editUserRequest.getName());
            preparedStatement.setString(3,editUserRequest.getSurname());
            preparedStatement.setString(4, editUserRequest.getRole());
            preparedStatement.setString(5, this.findEmail);
            preparedStatement.executeUpdate();
            return "success";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User findUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?");

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                );
                this.findEmail = email;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    //todo bool
    @Override
    public String setStatus(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        String status = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
              "UPDATE user SET status = CASE WHEN status = 1 THEN 0 WHEN status = 0 THEN 1 END WHERE email = ?"
            );
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();
            status = "success";
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User deleteUser(String email) {
        return null;
    }
}
