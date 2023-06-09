package rs.raf.publicnewstest.services;

import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.repository.news.NewsRepo;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    NewsRepo newsRepo;

    public List<News> getAllNews()
    {
        return this.newsRepo.getAllNews();
    }
    public News createNews(News news)
    {
        return this.newsRepo.createNews(news);
    }

    public boolean editNews(News news)
    {
        return this.newsRepo.editNews(news);
    }

    public News findNews(String title)
    {
        return this.newsRepo.findnews(title);
    }

    public List<News> getNewsByCategory(String category){return this.newsRepo.getNewsByCategory(category);}

    public boolean deleteNews(String title)
    {
        return this.newsRepo.deleteNews(title);
    }

    public List<News> getMostRead(){return this.newsRepo.getMostRead();}

}
