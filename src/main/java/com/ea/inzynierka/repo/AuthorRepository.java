package com.ea.inzynierka.repo;

import com.ea.inzynierka.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
