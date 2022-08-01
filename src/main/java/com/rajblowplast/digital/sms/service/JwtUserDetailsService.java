package com.rajblowplast.digital.sms.service;

import com.rajblowplast.digital.sms.model.AppUsers;
import com.rajblowplast.digital.sms.repository.UsersRepo;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("---loadUserByUsername called.---");
        AppUsers appUser = usersRepo.findByUsername(username);
        if(null != appUser && appUser.getUsername().equals(username)){
            Set<SimpleGrantedAuthority> authorities = new HashSet<>(1);
            authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
            LocalDateTime current = LocalDateTime.now();
            appUser.setId(appUser.getId());
            appUser.setLastLoginDate(current.format(AppConstants.dtf));
            usersRepo.save(appUser);
            return new User(appUser.getUsername(), appUser.getPassword(),true,true,true,!appUser.isLocked(), authorities);
        } else {
            throw new UsernameNotFoundException("User "+username+" not found.");
        }
    }

    public boolean existingUser(String email) throws Exception {
        return usersRepo.existsByEmail(email);
    }

    public AppUsers addUser(AppUsers registrationData) throws Exception{
        AppUsers newUser = new AppUsers();
        newUser.setUsername(registrationData.getUsername());
        newUser.setEmail(registrationData.getEmail());
        newUser.setMobileNo(registrationData.getMobileNo());
        newUser.setLocked(false);
        newUser.setLoggedIn(false);
        newUser.setRole("admin");
        newUser.setPassword(new BCryptPasswordEncoder().encode(registrationData.getPassword()));
        newUser.setLastLoginDate("0");
        LocalDateTime current = LocalDateTime.now();
        newUser.setRegistrationDate(current.format(AppConstants.dtf));
        usersRepo.save(newUser);
        return newUser;
    }
}
