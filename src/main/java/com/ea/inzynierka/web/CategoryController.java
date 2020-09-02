package com.ea.inzynierka.web;

import com.ea.inzynierka.exception.CategoryNotFound;
import com.ea.inzynierka.model.Category;
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
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView categoryListPage() {
        ModelAndView mav = new ModelAndView("categoriesList");
        List<Category> categoryList = categoryService.findAll();
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public ModelAndView newCategoryPage() {
        ModelAndView mav = new ModelAndView("addCategoryForm", "category", new Category());
        return mav;
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ModelAndView createNewCategory(@ModelAttribute @Valid Category category,
                                          BindingResult result,
                                          final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("addCategoryForm");
        }
        categoryService.create(category);

        ModelAndView mav = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("successMessage", "Category '" + category.getName() + "' has been added successfully.");

        return mav;
    }

    @RequestMapping(value = "/deleteCategory/{id}", method = RequestMethod.GET)
    public ModelAndView deleteCategory(@PathVariable Integer id,
                                       final RedirectAttributes redirectAttributes) throws CategoryNotFound {

        ModelAndView mav = new ModelAndView();
        Category category = categoryService.delete(id);
        mav.setViewName("info");
        mav.addObject("successMessage", "Category '" + category.getName() + "' was successfully deleted.");
        return mav;
    }
}
