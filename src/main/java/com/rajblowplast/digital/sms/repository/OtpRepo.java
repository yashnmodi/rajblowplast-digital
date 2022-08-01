package com.rajblowplast.digital.sms.repository;

import com.rajblowplast.digital.sms.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepo extends MongoRepository<Otp, String> {
    List<Otp> findByEmail(String userEmail);
}
