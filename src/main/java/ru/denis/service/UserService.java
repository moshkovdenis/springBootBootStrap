package ru.denis.service;

import ru.denis.model.Role;
import ru.denis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);

    List<User> allUsers();

    void updateUser(User user);

    User findById(Long id);

    void deleteUser(User user);

    Optional<User> findUserByName(String name);

    Optional<Role> findRoleByName(String roleName);
}
