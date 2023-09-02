package ru.antisessa.digitallibrary.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.antisessa.digitallibrary.models.Book;
import ru.antisessa.digitallibrary.models.Person;
import ru.antisessa.digitallibrary.models.Status;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByName(String name);

    List<Book> findByOwner(Person owner);

    List<Book> findBookByYearOfPublicationEquals(int year);

    List<Book> findBookByAuthorStartingWith(String startingWith);

    List<Book> findBookByStatusEquals(Status status);
}
