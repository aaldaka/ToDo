package com.ga.todoApp.service;

import com.ga.todoApp.exception.InformationExistException;
import com.ga.todoApp.exception.InformationNotFoundException;
import com.ga.todoApp.model.Category;
import com.ga.todoApp.model.User;
import com.ga.todoApp.repository.CategoryRepository;
import com.ga.todoApp.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }


    private final String UPLOAD_DIR = "uploads/";

    public Category createCategory(
            String name,
            String description,
            MultipartFile image) {

        System.out.println("Service Calling createCategory ==> ");

        Category category = categoryRepository.findByName(name);

        if (category != null) {
            throw new InformationExistException(
                    "category with name " + category.getName() + " already exists"
            );
        }

        Category newCategory = new Category();

        newCategory.setName(name);
        newCategory.setDescription(description);

        try {

            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Get original filename
            String originalFileName = image.getOriginalFilename();

            // Generate unique ID
            String uniqueId = UUID.randomUUID().toString();

            // Create unique filename
            String fileName = uniqueId + "_" + originalFileName;

            // Create file path
            Path filePath = uploadPath.resolve(fileName);

            // Save image
            image.transferTo(filePath);

            // Save image path in database
            newCategory.setImageUrl(UPLOAD_DIR + fileName);

        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }

        newCategory.setUser(getCurrentLoggedInUser()); //added
        return categoryRepository.save(newCategory);
    }

    public List<Category> getCategories(){
        System.out.println("Calling getCategories");
        return categoryRepository.findByUserId(getCurrentLoggedInUser().getId());
    }

    public Optional<Category> getCategory(Long categoryId){
        System.out.println("Service calling getCategory");
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null){
            throw new InformationNotFoundException("Category with that ID doesnt exist");
        }
        return categoryRepository.findById(categoryId);
    }

    public Category updateCategory(Long id, Category categoryObj){
        System.out.println("Service calling updateCategory");
        Category cat = categoryRepository.findByIdAndUserId(id, getCurrentLoggedInUser().getId());

        if (cat == null){
            throw new InformationNotFoundException("Category with that ID doesnt exist.");
        }
            cat.setName(categoryObj.getName());
            cat.setDescription(categoryObj.getDescription());
            return categoryRepository.save(cat);

    }

    public Category deleteCategory(Long id){
        System.out.println("Service calling deleteCategory ==>");
        Category cat = categoryRepository.findByIdAndUserId(id, CategoryService.getCurrentLoggedInUser().getId());

        if(cat == null){
            throw new InformationNotFoundException("Category with id "+ id+ " doesnt exist.");
        }else{
            categoryRepository.deleteById(id);
        }
        return cat;
    }

}
