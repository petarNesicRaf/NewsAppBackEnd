package rs.raf.publicnewstest.repository.user;

import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.requests.EditUserRequest;
import rs.raf.publicnewstest.requests.LoginRequest;

import java.util.List;

public interface UserRepo {

    User login(LoginRequest loginRequest);

    User createUser(User user);

    List<User> getAllUsers();

    String editUser(EditUserRequest editUserRequest);

    User findUser(String email);
    String setStatus(String email);
    public User deleteUser(String email);

}
