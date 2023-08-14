package ru.antisessa.digitallibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisessa.digitallibrary.models.Book;
import ru.antisessa.digitallibrary.models.Person;
import ru.antisessa.digitallibrary.models.Status;
import ru.antisessa.digitallibrary.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    //найти книги по названию
    public List<Book> findByName(String name){
        return booksRepository.findByName(name);
    }

    //найти книги по владельцу
    public List<Book> findByOwner(Person owner){
        return booksRepository.findByOwner(owner);
    }

    //найти книгу по дате издания
    public List<Book> findBookByDateOfPublicationEquals(Date date){
        return booksRepository.findBookByDateOfPublicationEquals(date);
    }

    //найти книгу по первым буквам ФИО автора
    public List<Book> findBookByAuthorStartingWith(String startingWith){
        return booksRepository.findBookByAuthorStartingWith(startingWith);
    }

    //найти книгу по статусу
    public List<Book> findBookByStatusEquals(Status status){
        return booksRepository.findBookByStatusEquals(status);
    }

    // CRUD - поиск, сохранение, изменение, удаление
    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
}
