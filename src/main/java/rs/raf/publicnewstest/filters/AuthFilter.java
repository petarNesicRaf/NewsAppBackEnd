package rs.raf.publicnewstest.filters;


import rs.raf.publicnewstest.resources.UserResource;
import rs.raf.publicnewstest.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //if(true) return;

        if(!this.isAuthRequired(requestContext))
        {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }
            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public boolean isAuthRequired(ContainerRequestContext req)
    {
        //ukoliko smo na loginu ne treba na autorizacija
        if(req.getUriInfo().getPath().contains("login"))
        {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for(Object matchedResource : matchedResources)
        {
            if(matchedResource instanceof UserResource ){
                return true;
            }
        }
        return false;
    }
}
