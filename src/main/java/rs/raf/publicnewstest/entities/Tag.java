package rs.raf.publicnewstest.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tag {
    private int id;
    @NotNull(message = "Keyword is required")
    @NotEmpty(message = "Keyword is required")
    private String keyword;

    public Tag() {
    }

    public Tag(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public Tag(String keyword) {
        this.keyword = keyword;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
