package com.rajblowplast.digital.sms.repository;

import com.rajblowplast.digital.sms.model.DispatchDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchRepo extends MongoRepository<DispatchDetails, String> {

    @Query(value = "{productType: '?0'}")
    List<DispatchDetails> findAllByProduct(String productType);
}
