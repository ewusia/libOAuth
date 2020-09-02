package com.ea.inzynierka.service;

import com.ea.inzynierka.exception.AuthorNotFound;
import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.repo.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Resource
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author create(Author author) {
        Author createdAuthor = author;
        authorRepository.save(createdAuthor);
        return createdAuthor;
    }

    @Override
    @Transactional(rollbackFor = AuthorNotFound.class)
    public Author delete(long id) throws AuthorNotFound {
        Optional<Author> authorOptional = authorRepository.findById(id);
        Author author = authorOptional.orElseThrow(AuthorNotFound::new);
        authorRepository.delete(author);
        return author;
    }

    @Override
    public List<Author> findAll() {
        List<Author> result = new ArrayList<>();
        Iterable<Author> all = authorRepository.findAll();
        all.forEach(result::add);
        return result;
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Long count() {
        return authorRepository.count();
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

}
