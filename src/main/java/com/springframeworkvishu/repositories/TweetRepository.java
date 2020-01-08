package com.springframeworkvishu.repositories;

import com.springframeworkvishu.domain.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
}
