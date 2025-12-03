package com.abdus.hospitalmanagement.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {
    @GetMapping
    ResponseEntity<?>get(){
        return  ResponseEntity.ok("please create account/signIn");
    }

}
