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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDTO createUser(UserDTO userDto) {

        UserEntity userEntity = new UserEntity();

        UserEntity userEmail = repository.findUserByEmail(userDto.getEmail()); // will check if a user has an email stored
        if(userEmail != null) throw new RuntimeException("User Email Already Exist.........");

        BeanUtils.copyProperties(userDto, userEntity); // data is transferred to entity

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword())); // not nullable and not sent as request-body
        userEntity.setUserId(utils.generateUserId(25)); // not nullable and not sent as request-body

        UserEntity userResponse = repository.save(userEntity); // those transferred entities are saved/copied to repositories

        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userResponse,userDTO); // those saved entities passed to DTO

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
