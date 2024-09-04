package com.example.user.service;

import com.example.user.entity.UserEntity;
import com.example.user.exception.EmailIdExistsException;
import com.example.user.exception.IdNotFoundException;
import com.example.user.exception.NoDataFoundException;
import com.example.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public boolean isEmailExists(String email) {
        boolean exists=false;
        try{
            log.info("email exists method started");
            exists= repository.findByEmail(email).isPresent();
        }
        catch(Exception e){
           log.error("Got error while checking email exists method",e);
           throw new RuntimeException("Got error while checking email is exists or not");
        }
finally{
            log.info("Email exists method is completed");
        }
        return exists;
    }



    @Override
    public UserEntity saveUser(UserEntity entity) {
        UserEntity savedUser=null;
        try{
            log.info("saving user method started");
            if(isEmailExists(entity.getEmail())){
                log.warn("Email already exists "+entity.getEmail());
               throw new EmailIdExistsException("Email id is already registered");
            }
            else{
                String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());
                entity.setPassword(hashedPassword);
                entity.setCreatedTime(LocalDateTime.now());
                entity.setUpdatedTime(LocalDateTime.now());
                entity.setDeletedTime(LocalDateTime.now());
                 savedUser= repository.save(entity);
                log.info("saved user successfully");
            }
        }
        catch (EmailIdExistsException e) {
            log.error("EmailIdExistsException occurred while saving user");
            throw e;
        }
        catch (Exception e) {
            log.error("Error occurred while saving user");
            throw new RuntimeException("Error occurred while saving user");
        }
        finally {
            log.info("Save user operation completed");
        }
        return savedUser;
    }




    @Override
    public Optional<UserEntity> findByIdCardNumber(String idCardNumber) {
        Optional<UserEntity> user = null;
    try{
        log.info("Searching for user by ID card number");
     user =repository.findByidCardNumber(idCardNumber);
        if(repository.findByidCardNumber(idCardNumber).isEmpty()){
            log.warn("user not found for the given ID card number");
            throw new IdNotFoundException("IdCardNumber not found");
        }
        log.info("User found for respective  ID card number");
    }
    catch (IdNotFoundException e) {
        log.error("IdNotFoundException occured ");
        throw e;
    }
    catch (Exception e) {
        log.error("Unexpected error occurred while finding user by ID card number");
        throw new RuntimeException("Error occurred while finding user by ID card number");
    }
    finally {
        log.info("Find user by ID card number operation completed for: " + idCardNumber);
    }
    return user;
    }




    @Override
    public List<UserEntity> findBycreatedTimeBetweenMethod(LocalDateTime startDate, LocalDateTime endDate) {
        List<UserEntity>userData=null;
       try{
           log.info("find by date method started");
          userData=repository.findBycreatedTimeBetween(startDate, endDate);
          if(userData.isEmpty()){

              throw new NoDataFoundException("No user data for the given date range");
          }
       }
      catch(NoDataFoundException ex){
          log.error("No user data for the given date range");
           throw ex;
      }
       catch(Exception e){
           log.error("Error occured while fetching data by the given date range");
           throw new RuntimeException("Error occured while fetching data by the given date range");
       }
       finally {
           log.info("find by method completed");
       }
        return userData;
    }


    @Override
    public List<UserEntity> findByUsernameMethod() {
        List<UserEntity> userlist=null;
        ArrayList<UserEntity>al=new ArrayList<>();
        HashSet<UserEntity>hs=new HashSet<>();
try{
    log.info("user name without duplicate method started");
    userlist=repository.findUniquePersonsByUsername();

    for(UserEntity entity:userlist){
        al.add(entity);
        hs.add(entity);
    }
    if(al.size()==hs.size()){
        log.info("fetched data by using username without duplicate");
    }
    else{
        log.info("duplicate value found");
    }
}
catch(Exception e){
    throw new RuntimeException(" Error occured while fetching data by using username");
}
finally{
    log.info("user name without duplicate method completed");
}
        return userlist;
    }


}
