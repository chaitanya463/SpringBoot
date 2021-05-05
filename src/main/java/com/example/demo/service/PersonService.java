package com.example.demo.service;

import com.example.demo.dao.PersonDAO;
import com.example.demo.model.Person;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    private PersonDAO personDAO;

    @Autowired
    public PersonService(@Qualifier("personDb") PersonDAO dao) {
        personDAO = dao;
    }

    public void addPerson(Person person) {
        personDAO.insertPerson(person);
    }

    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    public Optional<Person> getPersonById(UUID id) {
        return personDAO.getPersonById(id);
    }

    public int deletePersonById(UUID id) {
        return personDAO.deletePersonById(id);
    }

    public int updatePersonById(UUID id, Person person) {
        return personDAO.updatePersonById(id, person);
    }
}
