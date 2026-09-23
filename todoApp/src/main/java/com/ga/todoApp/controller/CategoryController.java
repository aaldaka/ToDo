package com.ga.todoApp.controller;

import com.ga.todoApp.model.Category;
import com.ga.todoApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo") //instead of api
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/categories")
    public List<Category> getCategories(){
        System.out.println("Calling getCategories");
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    public Category createCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        System.out.println("Calling createCategory ==> ");

        return categoryService.createCategory(name, description, image);
    }

    @GetMapping("/category")
    public Optional<Category> getCategory(@RequestParam("id") long id){
        System.out.println("Service calling getCategory");
        return categoryService.getCategory(id);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId")long id, @RequestParam(value = "name") String name,
                                   @RequestParam(value = "description") String desc){
        System.out.println("Service calling updateCategory");
        return categoryService.updateCategory(id, name, desc);
    }

    @DeleteMapping("/categories/{categoryId}")
    public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        System.out.println("calling deleteCategory ==>");
        return categoryService.deleteCategory(categoryId);
    }

}
