package ru.denis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.repository.RoleDao;
import ru.denis.repository.UserDao;
import ru.denis.model.Role;
import ru.denis.model.User;

import java.util.List;
import java.util.Optional;

@Service(value = "UserServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public List<User> allUsers() {
        return userDao.findAll();
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findUserById(id);
    }


    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return roleDao.findRoleByName(roleName);
    }

}
