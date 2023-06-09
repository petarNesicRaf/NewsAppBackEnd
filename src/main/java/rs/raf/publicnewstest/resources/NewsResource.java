package rs.raf.publicnewstest.resources;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/news")
public class NewsResource {

    @Inject
    NewsService newsService;

    @GET
    @Path("/allNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllNews()
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.newsService.getAllNews();
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

    @POST
    @Path("/createNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createNews(News news)
    {
        News ne = this.newsService.createNews(news);
        return Response.ok(ne).build();
    }

    @POST
    @Path("/editNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editNews(News news)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.editNews(news);
        if(!outcome)
        {
            response.put("message", "News modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "News successfully modified.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/findNews/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findNews(@PathParam("title") String title)
    {
        Map<String, String> response = new HashMap<>();
        News news = this.newsService.findNews(title);
        if(news == null)
        {
            response.put("message", "News with this title doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(news).build();
    }

    @GET
    @Path("/getNewsByCat/{category}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewsByCategory(@PathParam("category") String category)
    {
        Map<String, String> response = new HashMap<>();
        System.out.println("CATE " + category);
        List<News> newsByCategory = this.newsService.getNewsByCategory(category.toUpperCase());
        if(newsByCategory == null)
        {
            response.put("message", "No news in this category have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(newsByCategory).build();
    }
    @POST
    @Path("/deleteNews")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteNews(String news)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.deleteNews(news);
        if(!outcome)
        {
            response.put("message", "News and comments removal failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "News and comments successfully removed.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/mostRead")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMostRead()
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.newsService.getMostRead();
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

}
