package org.example.service;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements TableService<Role>{
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createEntity(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> readAllEntity() {
        return roleRepository.findAll();
    }

    @Override
    public Role readOneEntity(long id) {
        return roleRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Role role, long id) {
        role.setId(id);
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        roleRepository.deleteById(id);
        return true;
    }

    public Role findRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
