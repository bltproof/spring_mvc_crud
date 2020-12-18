package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void delete(User user);
    void delete(Long id);

    void update(User user);
}