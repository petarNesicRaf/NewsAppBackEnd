package rs.raf.publicnewstest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.publicnewstest.repository.category.CategoryRepo;
import rs.raf.publicnewstest.repository.category.MySqlCategoryRepo;
import rs.raf.publicnewstest.repository.comment.CommentRepo;
import rs.raf.publicnewstest.repository.comment.MySqlCommentRepo;
import rs.raf.publicnewstest.repository.news.MySqlNewsRepo;
import rs.raf.publicnewstest.repository.news.NewsRepo;
import rs.raf.publicnewstest.repository.tags.MySqlTagRepo;
import rs.raf.publicnewstest.repository.tags.TagRepo;
import rs.raf.publicnewstest.repository.user.MySqlUserRepo;
import rs.raf.publicnewstest.repository.user.UserRepo;
import rs.raf.publicnewstest.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Application extends ResourceConfig {
    public Application(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {

                this.bind(MySqlUserRepo.class).to(UserRepo.class).in(Singleton.class);
                this.bindAsContract(UserService.class);

                this.bind(MySqlCategoryRepo.class).to(CategoryRepo.class).in(Singleton.class);
                this.bindAsContract(CategoryService.class);

                this.bind(MySqlNewsRepo.class).to(NewsRepo.class).in(Singleton.class);
                this.bindAsContract(NewsService.class);

                this.bind(MySqlCommentRepo.class).to(CommentRepo.class).in(Singleton.class);
                this.bindAsContract(CommentService.class);


                this.bind(MySqlTagRepo.class).to(TagRepo.class).in(Singleton.class);
                this.bindAsContract(TagService.class);

            }
        };

        register(binder);

        packages("rs.raf.publicnewstest");
    }
}
