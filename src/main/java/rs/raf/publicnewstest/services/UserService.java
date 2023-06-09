package rs.raf.publicnewstest.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.repository.user.UserRepo;
import rs.raf.publicnewstest.requests.EditUserRequest;
import rs.raf.publicnewstest.requests.LoginRequest;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepo userRepo;

    public String login(LoginRequest loginRequest)
    {
        String hashedPassword = DigestUtils.sha256Hex(loginRequest.getPassword());
        loginRequest.setPassword(hashedPassword);

        User user = this.userRepo.login(loginRequest);
        if(user == null || !user.getPassword().equals(hashedPassword))
        {
            return null;
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(loginRequest.getEmail())
                .withClaim("role", user.getRole())
                .sign(algorithm);
    }

    public String createUser(User user)
    {
        String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashedPassword);
        User createdUser = userRepo.createUser(user);
        if(createdUser == null)
        {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(createdUser.getEmail())
                .withClaim("role", createdUser.getRole())
                .sign(algorithm);
    }

    public List<User> getAllUsers()
    {
        return this.userRepo.getAllUsers();
    }

    public User findUser(String email)
    {
        return this.userRepo.findUser(email);
    }

    public String editUser(EditUserRequest editUserRequest)
    {
        return this.userRepo.editUser(editUserRequest);
    }
    public String setStatus(String email)
    {
        return this.userRepo.setStatus(email);
    }

    public boolean isAuthorized(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        User user = this.userRepo.findUser(username);
        System.out.println("CLAIM " + jwt.getClaim("role").asString());
        if(jwt.getClaim("role").asString().equals("CONTENT_CREATOR"))
        {
            System.out.println("uslo");
            return false;
        }

        if(user == null)
            return false;

        return true;
    }


}
