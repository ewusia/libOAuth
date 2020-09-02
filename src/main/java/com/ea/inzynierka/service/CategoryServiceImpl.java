package com.ea.inzynierka.service;

import com.ea.inzynierka.exception.CategoryNotFound;
import com.ea.inzynierka.model.Category;
import com.ea.inzynierka.repo.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category create(Category category) {
        Category createdCategory = category;
        categoryRepository.save(createdCategory);
        return createdCategory;
    }

    @Override
    @Transactional(rollbackFor = CategoryNotFound.class)
    public Category delete(long id) throws CategoryNotFound {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.orElseThrow(CategoryNotFound::new);
        categoryRepository.delete(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();
        Iterable<Category> all = categoryRepository.findAll();
        all.forEach(result::add);
        return result;
    }

    @Override
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

}
