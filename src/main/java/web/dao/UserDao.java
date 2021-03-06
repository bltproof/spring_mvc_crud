package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void delete(User user);

    void update(User user);
}