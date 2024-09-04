package com.example.user.service;

import com.example.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    boolean isEmailExists(String email);
    UserEntity saveUser(UserEntity entity);
    Optional<UserEntity> findByIdCardNumber(String idCardNumber);
    List<UserEntity> findBycreatedTimeBetweenMethod(LocalDateTime startDate, LocalDateTime endDate);
    List<UserEntity> findByUsernameMethod();
}
