package ru.antisessa.digitallibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.antisessa.digitallibrary.models.Book;
import ru.antisessa.digitallibrary.models.Person;
import ru.antisessa.digitallibrary.models.Status;
import ru.antisessa.digitallibrary.services.BookService;
import ru.antisessa.digitallibrary.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

//    //Страница отображения всех книг
//    @GetMapping()
//    public String index(Model model) {
//        model.addAttribute("books", bookService.findAll());
//        return "books/index";
//    }

    //Страница отображения всех книг
    @GetMapping()
    public String index(Model model,
                              @RequestParam(defaultValue = "0", name = "page") int page,
                              @RequestParam(defaultValue = "0", name = "itemPerPage") int itemPerPage,
                              @RequestParam(defaultValue = "false", name = "sort_by_year") boolean sort_by_year) {
        model.addAttribute("books", bookService.findAllWithParam(page, itemPerPage, sort_by_year));
        return "books/index";
    }

    //Страница отображения книги по id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    //Страница создания книги
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    //Страница редактирования книги
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    //метод контролера для создания новой книги,
    // либо обратно на страницу /new если есть ошибки в полях,
    // с сохранением введенных данных
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        book.setStatus(Status.Free);
        bookService.save(book);
        return "redirect:/books";
    }

    //метод обновления книги с проверкой на ошибки в полях
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/setOwner/{id}")
    public String setOwner(@ModelAttribute("person") Person newOwner,
                           @PathVariable("id") int book_id){
        bookService.setOwner(book_id, newOwner);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @DeleteMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable("id") int id){
        bookService.deleteOwner(id);
        return "redirect:/books/{id}";
    }

}
