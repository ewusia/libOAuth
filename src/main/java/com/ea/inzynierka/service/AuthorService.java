package com.ea.inzynierka.service;

import com.ea.inzynierka.exception.AuthorNotFound;
import com.ea.inzynierka.model.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author create(Author author);
    Author delete(long id) throws AuthorNotFound;
    List<Author> findAll();
    Optional<Author> findById(long id);
    Long count();
    void save (Author author);
}
