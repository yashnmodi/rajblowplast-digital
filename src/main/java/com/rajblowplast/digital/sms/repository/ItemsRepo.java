package com.rajblowplast.digital.sms.repository;

import com.rajblowplast.digital.sms.model.ItemDetails;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepo extends MongoRepository<ItemDetails, String> {

    @DeleteQuery
    int deleteByName(String name);

    @Query
    boolean existsByName(String name);
}
