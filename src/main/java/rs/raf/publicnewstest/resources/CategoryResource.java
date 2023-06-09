package rs.raf.publicnewstest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.User;
import rs.raf.publicnewstest.repository.category.CategoryRepo;
import rs.raf.publicnewstest.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/categories")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @Path("/allCategories")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCategories()
    {
        Map<String, String> response = new HashMap<>();
        List<Category> allCategories = this.categoryService.getAllCategories();
        if(allCategories == null)
        {
            response.put("message", "No categories have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allCategories).build();
    }

    @POST
    @Path("/createCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createCategory(Category category)
    {
        Category cat = this.categoryService.createCategory(category);
        return Response.ok(cat).build();
    }
    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findCategory(@PathParam("name") String name)
    {
        Map<String, String> response = new HashMap<>();

        System.out.println("NAME  " + name);


        Category category = this.categoryService.findCategory(name);
        if(category == null)
        {
            response.put("message", "Category with this name doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(category).build();
    }

    @POST
    @Path("/editCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editCategory(@Valid Category category)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.categoryService.editCategory(category);
        if(!outcome)
        {
            response.put("message", "Category modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Category successfully modified.");
        return Response.status(200).entity(response).build();
    }

    @POST
    @Path("/deleteCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteCategory(String name)
    {
        Map<String, String> response = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(name, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            response.put("message", "Category removal failed.");
            return Response.status(422).entity(response).build();
        }
        String name1 = jsonMap.get("name");
        System.out.println("NAME   " +name1);
        boolean outcome = this.categoryService.deleteCategory(name1);
        if(!outcome)
        {
            response.put("message", "Category removal failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Category successfully removed.");
        return Response.status(200).entity(response).build();

    }
}
