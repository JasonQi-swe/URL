package com.example.letmeship.service.impl;

import com.example.letmeship.entity.Role;
import com.example.letmeship.repository.RoleRepository;
import com.example.letmeship.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
