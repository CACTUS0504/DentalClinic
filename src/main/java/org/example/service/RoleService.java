package org.example.service;

import org.example.model.Review;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) return roleOptional.get();
        else throw new IllegalArgumentException("Role with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Role role, long id) {
        role.setId(id);
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (roleRepository.findById(id).isPresent()){
            roleRepository.deleteById(id);
            return true;
        } else return false;
    }

    public Role findRoleByName(String name){
        return roleRepository.findByName(name);
    }
}
