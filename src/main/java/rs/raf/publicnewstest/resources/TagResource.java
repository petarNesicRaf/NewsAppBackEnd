package rs.raf.publicnewstest.resources;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.entities.Tag;
import rs.raf.publicnewstest.requests.TagRequest;
import rs.raf.publicnewstest.services.TagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/tags")
public class TagResource {
    @Inject
    TagService tagService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTags()
    {
        Map<String, String> response = new HashMap<>();
        List<Tag> allTags = this.tagService.getAllTags();
        if(allTags == null)
        {
            response.put("message", "No tags have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allTags).build();
    }


    @POST
    @Path("/createTag")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTag(Tag tag)
    {
        Tag t = this.tagService.createTag(tag);
        return Response.ok(t).build();
    }

    @POST
    @Path("/addTag")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addTagToNews(TagRequest tagRequest)
    {
        Map<String, String> response = new HashMap<>();

        boolean outcome = this.tagService.addTagToNews(tagRequest);
        if(!outcome)
        {
            response.put("message", "Adding this tag to news failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Tag successfully added.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/getTagsByNews/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTagsByNews(@PathParam("title") String title)
    {
        Map<String, String> response = new HashMap<>();
        List<Tag> allTags = this.tagService.getTagsByNews(title);
        if(allTags == null)
        {
            response.put("message", "No tags have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allTags).build();
    }

    @GET
    @Path("/getNewsByTag/{keyword}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewsByTag(@PathParam("keyword") String keyword)
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.tagService.getNewsByTag(keyword);
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }







}
