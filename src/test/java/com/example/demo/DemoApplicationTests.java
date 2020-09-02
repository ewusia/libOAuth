package com.example.demo;

import com.ea.inzynierka.Application;
import com.ea.inzynierka.exception.AuthorNotFound;
import com.ea.inzynierka.exception.BookNotFound;
import com.ea.inzynierka.exception.CategoryNotFound;
import com.ea.inzynierka.model.Author;
import com.ea.inzynierka.model.Book;
import com.ea.inzynierka.model.Category;
import com.ea.inzynierka.service.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class DemoApplicationTests extends AbstractDemoApplicationTests {

	private final static String IT = "IT";
	private final static String HISTORY = "History";
	private final static String CRIMINAL = "Criminal";
	private final static String CAY_S_HORSTMANN = "Cay S. Horstmann";
	private final static String NORMAN_DAVIES = "Norman Davies";
	private static final String AGATHA_CHRISTIE = "Agatha Christie";
	private static final String JAVA_PODSTAWY = "Java. Podstawy";
	private static final String HISTORIA_SWIATA = "Historia Świata";
	private static final String HARD_COVER = "hard-cover";
	private static final String YEAR = "2019";

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;

	@Override
	protected Long getId(Category category) {
		Long id = category.getId();
		Assert.assertNotNull(id);
		return id;
	}
	@Override
	protected Long getId(Author author) {
		Long id = author.getId();
		Assert.assertNotNull(id);
		return id;
	}
	@Override
	protected Long getId(Book book) {
		Long id = book.getId();
		Assert.assertNotNull(id);
		return id;
	}
	private void findCategoryById(long id, String categoryName) {
		Optional<Category> category = categoryService.findById(id);
		Assert.assertEquals(categoryName, category.get().getName());
	}
	private void findAuthorById(long id, String authorName) {
		Optional<Author> author = authorService.findById(id);
		Assert.assertEquals(authorName, author.get().getName());
	}
	private void findBookById(long id) {
		Optional<Book> book = Optional.ofNullable(bookService.findById(id));
		Assert.assertEquals(JAVA_PODSTAWY, book.get().getTitle());
		Assert.assertEquals(IT, book.get().getCategory().getName());
		Assert.assertEquals(HARD_COVER, book.get().getCover());
		Assert.assertEquals(YEAR, book.get().getYear());
		Assert.assertEquals(CAY_S_HORSTMANN, book.get().getAuthors().stream().findFirst().get().getName());
	}
	private Category addCategory(String categoryName) {
		Category category = new Category();
		category.setName(categoryName);
		categoryService.save(category);
		return category;
	}
	private Author addAuthor(String authorName) {
		Author author = new Author();
		author.setName(authorName);
		authorService.save(author);
		return author;
	}
	private Book addBook(Category category, Author author, String title, String year, String cover) {
		Set authors = new HashSet();
		authors.add(author);
		Book book = new Book();
		book.setTitle(title);
		book.setYear(year);
		book.setCover(cover);
		book.setCategory(category);
		book.setAuthors(authors);
		bookService.save(book);
		return book;
	}
	@Test
	public void contextLoads() {
	}
	@Test
	public void testFindCategoryById() {
		findCategoryById(-1, IT);
		return;
	}
	@Test
	public void testFindAuthorById() {
		findAuthorById(-1, CAY_S_HORSTMANN);
		return;
	}
	@Test
	//@Rollback(false)
	public void testAddCategory() {
		Category category = addCategory(HISTORY);
		Long id = getId(category);
		Optional<Category> newCategory = categoryService.findById(id);
		Assert.assertEquals(HISTORY, newCategory.get().getName());
		return;
	}
	@Test
	//@Rollback(false)
	public void testAddAuthor() {
		Author author = addAuthor(NORMAN_DAVIES);
		Long id = getId(author);
		Optional<Author> newAuthor = authorService.findById(id);
		Assert.assertEquals(NORMAN_DAVIES, newAuthor.get().getName());
		return;
	}
	@Test
	public void testRemoveCategory() throws CategoryNotFound {
		long startingNumberOfCategory = categoryService.count();
		Category category = addCategory(CRIMINAL);
		Long id = getId(category);
		Optional<Category> newCategory = categoryService.findById(id);
		Assert.assertEquals(CRIMINAL, newCategory.get().getName());
		Assert.assertEquals(4, startingNumberOfCategory+1);
		categoryService.delete(id);
		Assert.assertEquals(3, startingNumberOfCategory);
	}
	@Test
	public void testRemoveAuthor() throws AuthorNotFound {
		long startingNumberOfAuthor = authorService.count();
		Author author = addAuthor(AGATHA_CHRISTIE);
		Long id = getId(author);
		Optional<Author> newAuthor = authorService.findById(id);
		Assert.assertEquals("Agatha Christie", newAuthor.get().getName());
		Assert.assertEquals(6, startingNumberOfAuthor+1);
		authorService.delete(id);
		Assert.assertEquals(5, startingNumberOfAuthor);
	}
	@Test
	public void testFindBookById() {
		findBookById(-1);
		return;
	}
	@Test
	public void testAddBook() {
		Category category = addCategory(HISTORY);
		Long idc = getId(category);
		Optional<Category> newCategory = categoryService.findById(idc);
		Assert.assertEquals(HISTORY, newCategory.get().getName());

		Author author = addAuthor(NORMAN_DAVIES);
		Long ida = getId(author);
		Optional<Author> newAuthor = authorService.findById(ida);
		Assert.assertEquals(NORMAN_DAVIES, newAuthor.get().getName());

		Book book = addBook(category, author, HISTORIA_SWIATA, YEAR, HARD_COVER);
		Long idb = getId(book);
		Optional<Book> newBook = Optional.ofNullable(bookService.findById(idb));

		Assert.assertEquals(HISTORIA_SWIATA, newBook.get().getTitle());
		Assert.assertEquals(YEAR, newBook.get().getYear());
		Assert.assertEquals(HARD_COVER, newBook.get().getCover());
		Assert.assertEquals(HISTORY, newBook.get().getCategory().getName());
		Assert.assertEquals(NORMAN_DAVIES, newBook.get().getAuthors().stream().findFirst().get().getName());
		return;
	}

	@Test
	public void testRemoveBook() throws BookNotFound {
		long startingNumberOfBook = bookService.count();

		Category category = addCategory(HISTORY);
		Long idc = getId(category);
		Optional<Category> newCategory = categoryService.findById(idc);
		Assert.assertEquals(HISTORY, newCategory.get().getName());

		Author author = addAuthor(NORMAN_DAVIES);
		Long ida = getId(author);
		Optional<Author> newAuthor = authorService.findById(ida);
		Assert.assertEquals(NORMAN_DAVIES, newAuthor.get().getName());

		Book book = addBook(category, author, HISTORIA_SWIATA, YEAR, HARD_COVER);
		Long idb = getId(book);
		Assert.assertEquals(6, startingNumberOfBook+1);
		bookService.delete(idb);
		Assert.assertEquals(5, startingNumberOfBook);
	}
}
