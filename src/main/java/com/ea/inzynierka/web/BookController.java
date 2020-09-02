package com.ea.inzynierka.web;

import com.ea.inzynierka.exception.BookNotFound;
import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.model.Book;
import com.ea.inzynierka.model.Category;
import com.ea.inzynierka.repo.BookRepository;
import com.ea.inzynierka.service.AuthorService;
import com.ea.inzynierka.service.BookService;
import com.ea.inzynierka.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView bookListPage() {
        ModelAndView mav = new ModelAndView("index");
        List<Book> bookList = bookService.findAll();
        mav.addObject("bookList", bookList);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView newBookPage() {
        List<Author> authors = authorService.findAll();
        List<Category> categories = categoryService.findAll();
        ModelAndView mav = new ModelAndView("addBookForm", "book", new Book());
        mav.addObject("authorList", authors);
        mav.addObject("categoryList", categories);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView createNewBook(@ModelAttribute @Valid Book book,
                                      BindingResult result,
                                      final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Category> categories = categoryService.findAll();
            ModelAndView mav = new ModelAndView("addBookForm");
            mav.addObject("authorList", authors);
            mav.addObject("categoryList", categories);
            return mav;
        }
        bookService.create(book);

        ModelAndView mav = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("successMessage", "Book '" + book.getTitle() + "' has been added successfully.");

        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editBookPage(@PathVariable long id) {
        List<Author> authors = authorService.findAll();
        List<Category> categories = categoryService.findAll();

        Book book = bookService.findById(id);
        ModelAndView mav = new ModelAndView("editBook", "book", new Book());
        mav.addObject("authorList", authors);
        mav.addObject("categoryList", categories);
        mav.addObject("book", book);
        return mav;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public ModelAndView editBook(@ModelAttribute @Valid Book bookDetails,
                                 BindingResult result,
                                 @PathVariable long id,
                                 final RedirectAttributes redirectAttributes) throws BookNotFound {

        if (result.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Category> categories = categoryService.findAll();
            ModelAndView mav = new ModelAndView("editBook");
            mav.addObject("authorList", authors);
            mav.addObject("categoryList", categories);
            return mav;
        }
        bookService.edit(bookDetails);

        ModelAndView mav = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("successMessage", "Book '" + bookDetails.getTitle() + "' has been updated successfully.");

        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteBook(@PathVariable Integer id,
                                   final RedirectAttributes redirectAttributes) throws BookNotFound {

        Book book = bookService.delete(id);

        ModelAndView mav = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("successMessage", "Book '" + book.getTitle() + "' was successfully deleted.");

        return mav;
    }
}
