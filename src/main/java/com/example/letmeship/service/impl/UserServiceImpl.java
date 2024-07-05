package com.example.letmeship.service.impl;

import com.example.letmeship.entity.Role;
import com.example.letmeship.entity.User;
import com.example.letmeship.repository.RoleRepository;
import com.example.letmeship.repository.UserRepository;
import com.example.letmeship.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userDao;

    private RoleRepository roleDao;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userDao.save(new User(email, encodedPassword));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleDao.findByName(roleName);
        user.assignRoleToUser(role);
    }
}
