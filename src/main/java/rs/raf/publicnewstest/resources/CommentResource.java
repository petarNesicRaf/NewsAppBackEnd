package rs.raf.publicnewstest.resources;

import rs.raf.publicnewstest.entities.Comment;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/comments")
public class CommentResource {
    @Inject
    CommentService commentService;

    @GET
    @Path("{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findNews(@PathParam("title") String title)
    {
        Map<String, String> response = new HashMap<>();
        List<Comment> comments = this.commentService.getCommentsByNews(title);
        if(comments == null)
        {
            response.put("message", "Comments on this news doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(comments).build();
    }
    @POST
    @Path("/createComment")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComment( Comment comment)
    {

        Comment com = this.commentService.createComment(comment);
        return Response.ok(com).build();
    }
}
