/**
 * Created by:Wondafrash
 * Date : 6/17/2024
 * Time : 11:31 AM
 */
package com.example.userApp.controller;

import com.example.userApp.modle.request.UserDetialsRequestModle;
import com.example.userApp.modle.response.UserReponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping
    public String getUser(){
        return "User Details Called";
    }
    @PostMapping
    public UserReponse createUser(@RequestBody UserDetialsRequestModle requestModle){
        return null;
    }
}
