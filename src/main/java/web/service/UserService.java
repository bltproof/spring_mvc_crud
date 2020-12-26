package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    User getUserById(long id);

    User getUserByName(String username);

    List<User> getAllUsers();

    void delete(User user);

    void deleteById(Long id);

    void update(User user);
}