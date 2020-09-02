package com.ea.inzynierka.repo;

import com.ea.inzynierka.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
