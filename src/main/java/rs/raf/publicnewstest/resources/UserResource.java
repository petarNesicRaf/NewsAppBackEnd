package rs.raf.publicnewstest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.requests.EditUserRequest;
import rs.raf.publicnewstest.requests.LoginRequest;
import rs.raf.publicnewstest.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.login(loginRequest);
        if(jwt == null)
        {
            response.put("message", "These credentials do not match out records (Login)");
            return Response.status(422, "Unprocessable entity").entity(response).build();
        }
        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUser(@Valid User user)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.createUser(user);
        if(jwt == null)
        {
            response.put("message", "These credentials do not match out records (Login)");
            return Response.status(422, "Unprocessable entity").entity(response).build();
        }
        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers()
    {
        return Response.ok(this.userService.getAllUsers()).build();
    }

    @GET
    @Path("/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findUser(@PathParam("email") String email)
    {
        Map<String, String> response = new HashMap<>();
        System.out.println("email: " + email);
        User user = this.userService.findUser(email);
        if(user == null)
        {
            response.put("message", "User with this email doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Path("/editUser")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editUser(@Valid EditUserRequest editUserRequest)
    {
        Map<String, String> response = new HashMap<>();
        String outcome = this.userService.editUser(editUserRequest);
        if(outcome.equals("fail") || outcome == null)
        {
            response.put("message", "User modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "User successfully modified.");
        return Response.status(200).entity(response).build();
    }
    @POST
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    public Response setStatus(String email)
    {
        Map<String, String> response = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(email, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            response.put("message", "Category removal failed.");
            return Response.status(422).entity(email).build();
        }

        String email1 = jsonMap.get("email");
        String outcome = this.userService.setStatus(email1);
        if(outcome == null)
        {
            response.put("message", "Status modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Status successfully modified.");
        return Response.status(200).entity(response).build();
    }


}
