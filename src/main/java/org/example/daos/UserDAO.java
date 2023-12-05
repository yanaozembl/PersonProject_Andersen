package org.example.daos;

import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate; }

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User get(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user){
        jdbcTemplate.update(("INSERT INTO users(name, email) VALUES ( ?, ?)"),
            user.getName(), user.getEmail());
    }

    public void update(int id, User updatedUser){
        jdbcTemplate.update(("UPDATE users SET name=?, email=? WHERE id=?"),
                updatedUser.getName(), updatedUser.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update(("DELETE FROM users WHERE id=?"), id);
    }

}
