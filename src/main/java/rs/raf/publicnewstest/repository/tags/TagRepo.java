package rs.raf.publicnewstest.repository.tags;

import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.entities.Tag;
import rs.raf.publicnewstest.requests.TagRequest;

import java.util.List;

public interface TagRepo {

    List<Tag> getAllTags();

    Tag createTag(Tag tag);

    boolean addTagToNews(TagRequest tagRequest);

    List<Tag> getTagsByNews(String newsTitle);

    List<News> getNewsByTag(String keyword);


}
