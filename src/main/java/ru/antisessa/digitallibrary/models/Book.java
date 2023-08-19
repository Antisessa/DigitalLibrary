package ru.antisessa.digitallibrary.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 10, max = 100, message = "Name should be between 10 and 100 characters")
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Status status;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 6, max = 100, message = "Author name should be between 6 and 100 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "date_of_publication")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Дата должна быть не позже текущей")
    private Date dateOfPublication;

    @Column(name = "returndate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String name, String author, Date dateOfPublication) {
        this.name = name;
        this.author = author;
        this.dateOfPublication = dateOfPublication;
        this.status = Status.Free;
        this.returnDate = null;
        this.owner = null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", returnDate=" + returnDate +
                ", owner=" + owner +
                '}';
    }
}
