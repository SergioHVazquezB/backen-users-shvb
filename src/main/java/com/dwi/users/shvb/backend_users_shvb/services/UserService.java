package com.dwi.users.shvb.backend_users_shvb.services;

import java.util.List;
import java.util.Optional;

import com.dwi.users.shvb.backend_users_shvb.dto.UserDto;
import com.dwi.users.shvb.backend_users_shvb.models.entities.User;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(UserDto userDto);
    Optional<User> update(UserDto userDto, Long id);
    void deleteById(Long id);

}
