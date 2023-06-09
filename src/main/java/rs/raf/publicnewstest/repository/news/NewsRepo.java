package rs.raf.publicnewstest.repository.news;

import rs.raf.publicnewstest.entities.News;

import java.util.List;

public interface NewsRepo {

    List<News> getAllNews();

    List<News> getNewsByCategory(String category);
    News createNews(News news);

    boolean editNews(News news);

    News findnews(String title);

    boolean deleteNews(String title);

    List<News> getMostRead();


}
