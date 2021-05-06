package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository("postgres")
public class PersonDb implements PersonDAO{


    private  final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDb(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person(id,name,age) VALUES(:id,:name,:age)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", person.getName())
                .addValue("age", person.getAge());
        return jdbcTemplate.update(sql, source, holder);
    }

    @Override
    public List<Person> getAllPersons() {
        final String sql = "SELECT id, name, age FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            int age = Integer.parseInt(resultSet.getString("age"));
           return new Person(id, name, age);
        });
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        final String sql = "SELECT id, name, age FROM person WHERE id = :id";
        Map<String,Object> map = new HashMap<>();
        map.put("id" , id);
         return jdbcTemplate.queryForStream(sql, map, mapSelectedPerson()).findFirst();
    }

    private RowMapper<Person> mapSelectedPerson() {
        return ((resultSet, i) -> {
            UUID resultId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            int age = Integer.parseInt(resultSet.getString("age"));
            return new Person(resultId, name, age);
        });
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "DELETE FROM  person where id = :id";
        Map<String,Object> map = new HashMap<>();
        map.put("id" , id);
        return jdbcTemplate.update(sql, map);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sql = "UPDATE person SET name = :name, age = :age WHERE id = :id";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource()
            .addValue("id" , id)
            .addValue("name", person.getName())
            .addValue("age", person.getAge());
        return jdbcTemplate.update(sql, source, holder);
    }
}
