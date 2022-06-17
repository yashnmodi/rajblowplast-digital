package com.rajblowplast.digital.sms.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("Yash".equals(username)){
            return new User("Yash","$2a$10$O3Sz.oIdNZ6kv..NibSZju7JGmKXCb4QezFXl2V2Hpco.O8s.acY6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User "+username+" not found.");
        }
    }
}
