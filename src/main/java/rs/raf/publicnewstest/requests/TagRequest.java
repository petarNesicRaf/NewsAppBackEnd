package rs.raf.publicnewstest.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TagRequest {
    @NotNull(message = "News title is required")
    @NotEmpty(message = "News title is required")
    private String news_title;
    @NotNull(message = "Keyword is required")
    @NotEmpty(message = "Keyword is required")
    private String tag_keyword;

    public TagRequest(String news_title, String tag_keyword) {
        this.news_title = news_title;
        this.tag_keyword = tag_keyword;
    }

    public TagRequest() {
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getTag_keyword() {
        return tag_keyword;
    }

    public void setTag_keyword(String tag_keyword) {
        this.tag_keyword = tag_keyword;
    }
}
