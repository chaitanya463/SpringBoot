package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    PersonService service;

    @Autowired
    public PersonController(PersonService personService) {
        service = personService;
    }

    @PostMapping
    public void insertPerson(@Validated @NonNull @RequestBody Person person) {
        service.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return service.getAllPersons();
    }


    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return service.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID uuid) {
         service.deletePersonById(uuid);
    }

    @PutMapping(path = "{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @Validated @RequestBody Person person) {
         service.updatePersonById(id, person);
    }
}
