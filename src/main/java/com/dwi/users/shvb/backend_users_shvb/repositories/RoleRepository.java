package com.dwi.users.shvb.backend_users_shvb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dwi.users.shvb.backend_users_shvb.models.entities.Role;

public interface RoleRepository 
    extends CrudRepository<Role, Long>{

        Optional<Role> findByName(String name);

}
