package rs.raf.publicnewstest.services;

import rs.raf.publicnewstest.entities.Category;
import rs.raf.publicnewstest.repository.category.CategoryRepo;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    CategoryRepo categoryRepo;

    public List<Category> getAllCategories()
    {
        return this.categoryRepo.getAllCategories();
    }

    public Category createCategory(Category category)
    {
        return this.categoryRepo.createCategory(category);
    }

    public Category findCategory(String name)
    {
        return this.categoryRepo.findCategory(name);
    }

    public boolean editCategory(Category category)
    {
        return this.categoryRepo.editCategory(category);
    }

    public boolean deleteCategory(String name){
        return this.categoryRepo.deleteCategory(name);
    }
}
