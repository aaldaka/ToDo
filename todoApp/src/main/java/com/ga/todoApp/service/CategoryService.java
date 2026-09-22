package com.ga.todoApp.service;

import com.ga.todoApp.exception.InformationExistException;
import com.ga.todoApp.model.Category;
import com.ga.todoApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        System.out.println("Calling getCategories");
        return categoryRepository.findAll();
    }

    public Category createCategory(Category categoryObj){
        System.out.println("Service calling createCategory");

        Category category = categoryRepository.findByName(categoryObj.getName());
        if (category!=null){
            throw new InformationExistException("Category with name " + category.getName() +" already exists/");
        }else{
            return categoryRepository.save(categoryObj);
        }
    }

    public Optional<Category> getCategory(Long id){
        System.out.println("Service calling getCategory");
        return categoryRepository.findById(id);
    }

// Attempt for update done in class
//    public Category updateCategory(long id, String name, String desc){
//        System.out.println("Service calling updateCategory");
//        Optional<Category> cat = categoryRepository.findById(id);
//
//        if(cat.isEmpty()){
//            throw new InformationNotFoundException("Category: "+ id + " doesnt exist.");
//        }else{
//            Category cate = cat.get();
//            cate.setName(name);
//            cate.setDescription(desc);
//            return categoryRepository.save(cate);
//        }
//    }

}
