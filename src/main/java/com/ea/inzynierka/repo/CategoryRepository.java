package com.ea.inzynierka.repo;

import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
