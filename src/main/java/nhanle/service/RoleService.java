package nhanle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhanle.entity.Role;
import nhanle.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    
    public Optional<Role> findByName(Role.RoleName name) {
        return roleRepository.findByName(name);
    }
    
    public Role findOrCreateRole(Role.RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    public boolean existsByName(Role.RoleName name) {
        return roleRepository.existsByName(name);
    }
}