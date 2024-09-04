package com.example.user.controller;

import com.example.user.entity.UserEntity;
import com.example.user.exception.ApiResponse;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<ApiResponse<UserEntity>> createUser(@RequestBody UserEntity entity){
        UserEntity savedUser=service.saveUser(entity);
        ApiResponse<UserEntity> response = ApiResponse.<UserEntity>builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(savedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{idCardNumber}")
    public ResponseEntity<ApiResponse<UserEntity>> getUserByIdCardNumber(@PathVariable String idCardNumber) {
        Optional<UserEntity> user = service.findByIdCardNumber(idCardNumber);
        ApiResponse<UserEntity>response=ApiResponse.<UserEntity>builder()
                .message("User found")
                .status(HttpStatus.OK.value())
                .data(user.get())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>>getUserByCreatedDate(@RequestParam("start") String startDate, @RequestParam("end") String endDate){
        // HTTP query parameters are strings, so conversion to LocalDateTime is necessary to work with date-time objects
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
      List<UserEntity>userData= service.findBycreatedTimeBetweenMethod(start, end);
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/names")
    public ResponseEntity<List<UserEntity>> getDistinctItems() {
        List<UserEntity> response=service.findByUsernameMethod();
        return ResponseEntity.ok(response);
    }
}
