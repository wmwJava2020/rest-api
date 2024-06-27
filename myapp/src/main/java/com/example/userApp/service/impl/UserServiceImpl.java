/**
 * Created by:AIDA
 * Date : 6/17/2024
 * Time : 12:52 PM
 */
package com.example.userApp.service.impl;

import com.example.userApp.entity.UserEntity;
import com.example.userApp.repository.UserRepository;
import com.example.userApp.service.UserService;
import com.example.userApp.shared.UserDTO;
import com.example.userApp.shared.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository repository;
    @Autowired
    Utils utils;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //@Override
    public UserDTO createUser(UserDTO user) {

        UserEntity userEntity = new UserEntity();

        UserEntity userEmail = repository.findUserByEmail(user.getEmail()); // will check if a user has an email stored
        if(userEmail != null) throw new RuntimeException("User Email Already Exist.........");

        BeanUtils.copyProperties(user, userEntity); // data is transferred to entity

        log.info("Data copied to userDTO objects ",user);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword())); // not nullable and not sent as request-body
        userEntity.setUserId(utils.generateUserId(25)); // not nullable and not sent as request-body

        UserEntity userResponse = repository.save(userEntity); // those transferred entities are saved/copied to repositories

        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userResponse,userDTO); // those saved entities passed to DTO
        log.info("Data copied to userResponse object with BeaUtil.......",userResponse);

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findUserByEmail(username);

        log.info("User Entity Loaded From Repository",userEntity);

        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(username,userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
