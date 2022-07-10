package com.fampay.sortytsearch.repository;

import com.fampay.sortytsearch.dbModel.Videos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends MongoRepository<Videos,String> {
    @Query("{videoTitle:'?0'}")
    Videos findVideoByTitle(String title);
    @Query("{description:'?0'}")
    Videos findVideoByDescription(String description);
    List<Videos> findAllBy(TextCriteria criteria);
}
