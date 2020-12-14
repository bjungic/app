package com.java.spring.app.services;

import com.java.spring.app.security.Role;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(String role) {
        Iterable<Role> result = roleRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Role r = (Role) iter.next();
            if (!role.equals(r.getName())) {
                roleRepository.save(new Role(role.toUpperCase()));
            }
        }
    }
    public Iterable<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
