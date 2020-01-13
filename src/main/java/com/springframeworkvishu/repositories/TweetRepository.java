package com.springframeworkvishu.repositories;

import com.springframeworkvishu.domain.Tweet;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
    List<Tweet> findAllByOrderByDateAsc();

    List<Tweet> findAll(Sort date);
}
