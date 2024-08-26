package com.dwi.users.shvb.backend_users_shvb.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwi.users.shvb.backend_users_shvb.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.dwi.users.shvb.backend_users_shvb.models.entities.User> user =
             userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(
                    String.format("El usuario %s no existe", username));
        }
        com.dwi.users.shvb.backend_users_shvb.models.entities.User userBd = 
            user.orElseThrow();

        List<GrantedAuthority> authorities = userBd.getRoles()
            .stream().map(rol -> new SimpleGrantedAuthority(rol.getName()))
            .collect(Collectors.toList());

        return new User(userBd.getUsername(),
                userBd.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

}
