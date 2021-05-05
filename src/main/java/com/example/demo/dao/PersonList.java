package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personDb")
public class PersonList implements PersonDAO{
    List<Person> list = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        list.add(new Person(id, person.getName(), person.getAge()));
        return 1;
    }

    @Override
    public List<Person> getAllPersons() {
        return list;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return list.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = getPersonById(id);
        if (person.isPresent()) {
            list.remove(person.get());
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return getPersonById(id).map(person1 -> {
            int index = list.indexOf(person1);
            if (index >= 0) {
                list.set(index, new Person(id, person.getName(), person.getAge()));
                return 1;
            }
            return 0;
        }).orElse(0);

    }


}
