package com.ea.inzynierka.web;

import com.ea.inzynierka.exception.AuthorNotFound;
import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.service.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView authorListPage() {
        ModelAndView mav = new ModelAndView("authorsList");
        List<Author> authorList = authorService.findAll();
        mav.addObject("authorList", authorList);
        return mav;
    }

    @RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
    public ModelAndView newAuthorPage() {
        ModelAndView mav = new ModelAndView("addAuthorForm", "author", new Author());
        return mav;
    }

    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
    public ModelAndView createNewAuthor(@ModelAttribute @Valid Author author,
                                        BindingResult result,
                                        final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("addAuthorForm");
        }
        authorService.create(author);

        ModelAndView mav = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("successMessage", "Author '" + author.getName() + "' has been added successfully.");

        return mav;
    }

    @RequestMapping(value = "/deleteAuthor/{id}", method = RequestMethod.GET)
    public ModelAndView deleteAuthor(@PathVariable Integer id,
                                     final RedirectAttributes redirectAttributes) throws AuthorNotFound {

        ModelAndView mav = new ModelAndView();
        Author author = authorService.delete(id);
        mav.setViewName("info");
        mav.addObject("successMessage", "Author '" + author.getName() + "' was successfully deleted.");
        return mav;
    }
}
