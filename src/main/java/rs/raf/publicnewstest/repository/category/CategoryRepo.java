package rs.raf.publicnewstest.repository.category;

import rs.raf.publicnewstest.entities.Category;

import java.util.List;

public interface CategoryRepo {

    List<Category> getAllCategories();
    Category createCategory(Category category);
    boolean editCategory(Category category);
    Category findCategory(String name);
    boolean deleteCategory(String name);

}
