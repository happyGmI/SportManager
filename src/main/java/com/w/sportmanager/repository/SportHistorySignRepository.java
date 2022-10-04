package com.w.sportmanager.repository;

import com.w.sportmanager.pojo.SportSignHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SportHistorySignRepository extends MongoRepository<SportSignHistory, String> {
    SportSignHistory getSportSignHistoryById(String id);
}
