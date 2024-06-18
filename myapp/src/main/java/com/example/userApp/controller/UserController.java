/**
 * Created by:Wondafrash
 * Date : 6/17/2024
 * Time : 11:31 AM
 */
package com.example.userApp.controller;

import com.example.userApp.modle.request.UserDetialsRequestModle;
import com.example.userApp.modle.response.UserReponse;
import com.example.userApp.service.impl.UserServiceImpl;
import com.example.userApp.shared.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/all")
    public String getUser(){
        return "User Details Called";
    }
    @PostMapping("/data")
    public UserReponse createUser(@RequestBody UserDetialsRequestModle requestModle){

        UserReponse userReponse = new UserReponse(); // initiate UserRestResponse

        UserDTO userDTO = new UserDTO();  // userDTO used to transfer data from source to destination

        BeanUtils.copyProperties(requestModle,userDTO);  // BeanUtils/spring provided method, copies data from source to destinations

        UserDTO createUser = userService.createUser(userDTO); // will create users from service impl class

        BeanUtils.copyProperties(createUser,userReponse); // copy created users from source to destinations

        return userReponse;
    }
}
