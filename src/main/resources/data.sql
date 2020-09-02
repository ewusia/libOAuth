SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE new.book_author;
TRUNCATE TABLE new.book;
TRUNCATE TABLE new.author;
TRUNCATE TABLE new.category;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO new.author (author_id, name) VALUES (-1, 'Cay S. Horstmann');
INSERT INTO new.author (author_id, name) VALUES (-2, 'Robert C. Martin');
INSERT INTO new.author (author_id, name) VALUES (-3, 'Ewa Dąbrowska');
INSERT INTO new.author (author_id, name) VALUES (-4, 'Alexander A. Stepanov');
INSERT INTO new.author (author_id, name) VALUES (-5, 'Daniel E. Ros');

INSERT INTO new.category (category_id, name) VALUES (-1, 'IT');
INSERT INTO new.category (category_id, name) VALUES (-2, 'Health');
INSERT INTO new.category (category_id, name) VALUES (-3, 'Maths');

INSERT INTO new.book (book_id, category_id, cover, title, year)
VALUES (-1, -1, 'hard-cover', 'Java. Podstawy', '2019');
INSERT INTO new.book (book_id, category_id, cover, title, year)
VALUES (-2, -1, 'hard-cover', 'Java. Techniki zaawansowane', '2019');
INSERT INTO new.book (book_id, category_id, cover, title, year)
VALUES (-3, -1, 'soft-cover', 'Czysty kod', '2017');
INSERT INTO new.book (book_id, category_id, cover, title, year)
VALUES (-4, -2, 'soft-cover', 'Dieta Dąbrowskiej', '2018');
INSERT INTO new.book (book_id, category_id, cover, title, year)
VALUES (-5, -3, 'soft-cover', 'Od matematyki do programowania uogólnionego', '2015');


INSERT  INTO new.book_author(book_id, author_id) VALUES (-1,-1);
INSERT  INTO new.book_author(book_id, author_id) VALUES (-2,-1);
INSERT  INTO new.book_author(book_id, author_id) VALUES (-3,-2);
INSERT  INTO new.book_author(book_id, author_id) VALUES (-4,-3);
INSERT  INTO new.book_author(book_id, author_id) VALUES (-5,-4);
INSERT  INTO new.book_author(book_id, author_id) VALUES (-5,-5);

commit;

