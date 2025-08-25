package org.skypro.Star.Bank.service;

import org.skypro.Star.Bank.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findUsersByName(String name) {
        String sql = "SELECT id, name, surname FROM user WHERE CONCAT(name, ' ', surname) ILIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),
                "%" + name + "%"
        );
    }
}
