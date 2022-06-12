package com.rajblowplast.digital.sms.repository;

import com.rajblowplast.digital.sms.model.ManufacturedDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufactureRepo extends MongoRepository<ManufacturedDetails, String> {

    @Query(value = "{productType: '?0'}")
    List<ManufacturedDetails> findAllByProduct(String productType);

}
