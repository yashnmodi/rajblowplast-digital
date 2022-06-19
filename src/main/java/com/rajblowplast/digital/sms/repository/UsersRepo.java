package com.rajblowplast.digital.sms.repository;

import com.rajblowplast.digital.sms.model.AppUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends MongoRepository<AppUsers, String> {
    @Query(value = "{username: '?0'}")
    AppUsers findByUsername(String username);
}
