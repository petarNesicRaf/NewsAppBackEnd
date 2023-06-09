package rs.raf.publicnewstest.services;

import rs.raf.publicnewstest.entities.News;
import rs.raf.publicnewstest.entities.Tag;
import rs.raf.publicnewstest.repository.tags.TagRepo;
import rs.raf.publicnewstest.requests.TagRequest;

import javax.inject.Inject;
import java.util.List;

public class TagService {
    @Inject
    TagRepo tagRepo;

    public List<Tag> getAllTags()
    {
        return this.tagRepo.getAllTags();
    }

    public Tag createTag(Tag tag)
    {
        return this.tagRepo.createTag(tag);
    }

    public boolean addTagToNews(TagRequest tagRequest)
    {
        return this.tagRepo.addTagToNews(tagRequest);
    }

    public List<Tag> getTagsByNews(String title)
    {
        return this.tagRepo.getTagsByNews(title);
    }

    public List<News> getNewsByTag(String keyword)
    {
        return this.tagRepo.getNewsByTag(keyword);
    }


}
