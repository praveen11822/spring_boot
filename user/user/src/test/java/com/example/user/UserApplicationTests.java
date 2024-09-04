package com.example.user;

import com.example.user.entity.UserEntity;
import com.example.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private UserRepository userRepository;




    @Test
    public void setUp() {
        // Initialize a user instance for use in tests
      UserEntity  testUser = new UserEntity();
        testUser.setUsername("testUser1");
        testUser.setEmail("test8@gmail.com");
       testUser.setPassword(BCrypt.hashpw(testUser.getPassword(), BCrypt.gensalt()));
        testUser.setDob(LocalDate.of(2024, 8, 7));
      testUser.setIdCardNumber("80808");
        testUser.setIdCardName("visiting card");
        testUser.setCreatedTime(LocalDateTime.now());
        testUser.setAddress("trichy");
        testUser.setDeletedTime(LocalDateTime.now());
        testUser.setUpdatedTime(LocalDateTime.now());
        testUser.setIsDeleted(false);

        testEmailIdExists(testUser);
       userRepository.save(testUser);
        testSaveUser_Success(testUser);
        testFindByIdCardNumber_Success(testUser);
        testFindByIdCardNumber_NotFound(testUser);

    }


    public void testEmailIdExists(UserEntity testUser){

        Optional<UserEntity> foundEmail = userRepository.findByEmail(testUser.getEmail());
        assertFalse(foundEmail.isPresent(), "Email already registered");

    }



    public void testSaveUser_Success(UserEntity testUser) {
        UserEntity savedUser = userRepository.save(testUser);
        assertNotNull(savedUser.getId(), "User ID should not be null");
        assertEquals("testUser1", savedUser.getUsername(), "Username should match");

    }



    public void testFindByIdCardNumber_Success(UserEntity testUser){

        Optional<UserEntity> foundUser = userRepository.findByidCardNumber(testUser.getIdCardNumber());
        assertTrue(foundUser.isPresent(), "User should be found by ID card number");
        assertEquals("testUser1", foundUser.get().getUsername(), "Username should match");
    }



    public void testFindByIdCardNumber_NotFound(UserEntity testUser) {
        Optional<UserEntity> foundUser = userRepository.findByidCardNumber(testUser.getIdCardNumber());
        assertTrue(foundUser.isPresent(), "User should not be found by nonexistent ID card number");
    }

    @Test
    public void findBycreatedTimeBetweenMethod(){
        LocalDateTime startDate=LocalDateTime.of(2024,8,07,00,00,00,00);
        LocalDateTime endDate=LocalDateTime.of(2024,8,13,00,00,00,00);

        List<UserEntity> dates=userRepository.findBycreatedTimeBetween(startDate,endDate);
        assertNotNull(dates, "The list of users should not be null.");
        assertFalse(dates.isEmpty(), "The list of users should not be empty.");
    }

}
