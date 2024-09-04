package com.example.user.repository;

import com.example.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {


    Optional<UserEntity> findByidCardNumber(String number);
    Optional<UserEntity> findByEmail(String emailid);
    List<UserEntity> findBycreatedTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select distinct on (username) * from userdetails order by username, id", nativeQuery = true)
    List<UserEntity> findUniquePersonsByUsername();

}
