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
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    public User get(int id) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user){
        jdbcTemplate.update(("INSERT INTO Users(name, email) VALUES ( ?, ?)"),
            user.getName(), user.getEmail());
    }

    public void update(int id, User updatedUser){
        jdbcTemplate.update(("UPDATE Users SET name=?, email=? WHERE id=?"),
                updatedUser.getName(), updatedUser.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update(("DELETE FROM Users WHERE id=?"), id);
    }

}
