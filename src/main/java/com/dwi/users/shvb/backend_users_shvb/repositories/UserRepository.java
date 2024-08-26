package com.dwi.users.shvb.backend_users_shvb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dwi.users.shvb.backend_users_shvb.models.entities.User;

public interface UserRepository 
        extends CrudRepository<User, Long> {

        Optional<User> findByUsername(String username);
        
        @Query("SELECT u FROM User u WHERE u.username = ?1")
        Optional<User> getUserByUsername(String username);
        

}
