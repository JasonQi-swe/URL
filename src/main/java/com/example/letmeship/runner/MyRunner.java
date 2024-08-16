package com.example.letmeship.runner;

import com.example.letmeship.repository.RoleRepository;
import com.example.letmeship.repository.UserRepository;
import com.example.letmeship.service.RoleService;
import com.example.letmeship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (isDatabaseEmpty()) {
            createRoles();
            createAdmin();
            createOtherUsers();
        }
    }

    private void createRoles() {
        Arrays.asList("Admin", "User").forEach(role -> roleService.createRole(role));
    }

    private void createAdmin() {
        userService.createUser("admin@gmail.com", "1234");
        userService.assignRoleToUser("admin@gmail.com", "Admin");
    }

    private void createOtherUsers() {
        for (int i = 0; i < 10; i++) {
            String email = "user" + i + "@gmail.com";
            userService.createUser(email, "1234");
            userService.assignRoleToUser(email, "User");
        }
    }

    private boolean isDatabaseEmpty() {
        return userRepository.count() == 0 && roleRepository.count() == 0;
    }
}
