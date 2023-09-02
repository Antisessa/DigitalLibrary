package ru.antisessa.digitallibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisessa.digitallibrary.models.Book;
import ru.antisessa.digitallibrary.models.Person;
import ru.antisessa.digitallibrary.models.Status;
import ru.antisessa.digitallibrary.repositories.BooksRepository;

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
    public List<Book> findBookByDateOfPublicationEquals(int year){
        return booksRepository.findBookByYearOfPublicationEquals(year);
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

    public List<Book> findAllWithParam(int page, int itemsPerPage, boolean sort_by_year) {
        if (page == 0 && itemsPerPage == 0 && !sort_by_year) {
            return booksRepository.findAll();
        } else if (page == 0 && itemsPerPage == 0 && sort_by_year) {
            return booksRepository.findAll(Sort.by("yearOfPublication"));
        } else if (itemsPerPage != 0 && !sort_by_year) {
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage,
                    Sort.by("yearOfPublication"))).getContent();
        }
    }

    @Transactional
    public void save(Book book) {
        book.setStatus(Status.Free);
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void setOwner(int book_id, Person person){
        Book book = booksRepository.findById(book_id).orElse(null);

        if(book != null){
            book.setOwner(person);
            book.setStatus(Status.Busy);
            booksRepository.save(book);
        }
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void deleteOwner(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        Book book = foundBook.orElse(null);

        if(book != null){
            book.setOwner(null);
            book.setStatus(Status.Free);
            book.setReturnDate(null);
        }
    }
}
