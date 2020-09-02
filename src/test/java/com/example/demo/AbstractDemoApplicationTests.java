package com.example.demo;

import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.model.Book;
import com.ea.inzynierka.model.Category;

public abstract class AbstractDemoApplicationTests {
    protected abstract Long getId(Category category);
    protected abstract Long getId(Author author);
    protected abstract Long getId(Book book);
}
