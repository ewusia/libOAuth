package com.ea.inzynierka.service;

import com.ea.inzynierka.exception.BookNotFound;
import com.ea.inzynierka.model.Book;
import com.ea.inzynierka.repo.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookRepository bookRepository;

    @Override
    @Transactional
    public Book create(Book book) {
        Book createdBook = book;
        bookRepository.save(createdBook);
        return createdBook;
    }

    @Override
    @Transactional(rollbackFor=BookNotFound.class)
    public Book edit(Book bookDetails) throws BookNotFound {
        Optional<Book> book = bookRepository.findById(bookDetails.getId());
        Book updatedBook = book.orElseThrow(BookNotFound::new);

        updatedBook.setTitle(bookDetails.getTitle());
        updatedBook.setAuthors(bookDetails.getAuthors());
        updatedBook.setYear(bookDetails.getYear());
        updatedBook.setCategory(bookDetails.getCategory());
        updatedBook.setCover(bookDetails.getCover());

        updatedBook = bookRepository.save(updatedBook);
        return updatedBook;
    }

    @Override
    @Transactional(rollbackFor = BookNotFound.class)
    public Book delete(long id) throws BookNotFound {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = bookOptional.orElseThrow(BookNotFound::new);

        bookRepository.delete(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<Book>();
        Iterable<Book> all = bookRepository.findAll();
        all.forEach(result::add);

        return result;
    }

    @Override
    public Book findById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        return book;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

}
