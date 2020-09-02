package com.ea.inzynierka.service;

import com.ea.inzynierka.exception.BookNotFound;
import com.ea.inzynierka.model.Book;
import java.util.List;

public interface BookService {

    Book create(Book book);
    Book edit(Book book) throws BookNotFound;
    Book delete(long id) throws BookNotFound;
    List<Book> findAll();
    Book findById(long id);
    void save(Book book);
    long count();
}
