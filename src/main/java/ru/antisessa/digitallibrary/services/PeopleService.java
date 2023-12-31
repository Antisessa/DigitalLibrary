package ru.antisessa.digitallibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antisessa.digitallibrary.models.Person;
import ru.antisessa.digitallibrary.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findByName(String name){
        return peopleRepository.findByName(name);
    }

    //найти людей по фио отсортированных по возрасту
    public List<Person> findByNameOrderByAge(String name){
        return peopleRepository.findByNameOrderByAge(name);
    }

    //найти людей по началу ФИО
    public List<Person> findByNameStartingWith(String startingWith){
        return peopleRepository.findByNameStartingWith(startingWith);
    }

    // CRUD - поиск, сохранение, изменение, удаление
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
