package com.ga.todoApp.service;

import com.ga.todoApp.exception.InformationExistException;
import com.ga.todoApp.exception.InformationNotFoundException;
import com.ga.todoApp.model.Category;
import com.ga.todoApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        return categoryRepository.save(newCategory);
    }

    public List<Category> getCategories(){
        System.out.println("Calling getCategories");
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id){
        System.out.println("Service calling getCategory");
        return categoryRepository.findById(id);
    }

    public Category updateCategory(long id, String name, String desc){
        System.out.println("Service calling updateCategory");
        Optional<Category> cat = categoryRepository.findById(id);

        if(cat.isEmpty()){
            throw new InformationNotFoundException("Category: "+ id + " doesnt exist.");
        }else{
            Category cate = cat.get();
            cate.setName(name);
            cate.setDescription(desc);
            return categoryRepository.save(cate);
        }
    }

    public Optional<Category> deleteCategory(Long id){
        System.out.println("Service calling deleteCategory ==>");
        Optional<Category> cat = categoryRepository.findById(id);

        if(cat.isEmpty()){
            throw new InformationNotFoundException("Category with id "+ id+ " doesnt exist.");
        }else{
            categoryRepository.deleteById(id);
        }
        return cat;
    }

}
