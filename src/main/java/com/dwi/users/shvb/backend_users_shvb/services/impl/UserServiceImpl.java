package com.dwi.users.shvb.backend_users_shvb.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwi.users.shvb.backend_users_shvb.dto.UserDto;
import com.dwi.users.shvb.backend_users_shvb.models.entities.Role;
import com.dwi.users.shvb.backend_users_shvb.models.entities.User;
import com.dwi.users.shvb.backend_users_shvb.repositories.RoleRepository;
import com.dwi.users.shvb.backend_users_shvb.repositories.UserRepository;
import com.dwi.users.shvb.backend_users_shvb.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        Optional<Role> rolOptional  =
            roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if(rolOptional.isPresent()){
            roles.add(rolOptional.orElseThrow());
        }
        //rolOptional.ifPresent(roles::add);

        User userDb = new User();
        userDb.setUsername(userDto.getUsername());
        userDb.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDb.setEmail(userDto.getEmail());
        userDb.setRoles(roles);

        return userRepository.save(userDb);
    }

    @Override
    @Transactional
    public Optional<User> update(UserDto userDto, Long id) {
        Optional<User> user = this.findById(id);
        User userReturn = null;
        if(user.isPresent()){
            User userdb = user.orElseThrow();
            userdb.setUsername(userDto.getUsername());
            userdb.setEmail(userDto.getEmail());
            userReturn = userRepository.save(userdb);
        }
        return Optional.ofNullable(userReturn);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
