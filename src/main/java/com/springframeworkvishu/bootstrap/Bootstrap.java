package com.springframeworkvishu.bootstrap;

import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public Bootstrap(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("DODO: Loading tweets");
        loadInitialTweets();
        loadInitialUsers();
    }

    private void loadInitialUsers() {
        log.debug("DODO: Loading initial users");
        User user1 = new User();
        user1.setUsername("roxanne");
        user1.setPassword("roxanne");
        user1.setEmail("roxanne@example.com");

        userRepository.save(user1);

        log.debug("DODO: Total Users Saved: " + userRepository.count());
    }

    private void loadInitialTweets() {
        log.debug("DODO: Loading initial tweets");
        Tweet tweet1 = new Tweet();
        tweet1.setOpinion("This project is going to be completed");
        tweet1.setDate(new Date());

        Tweet tweet2 = new Tweet();
        tweet2.setOpinion("This project is going to be awesome");
        tweet2.setDate(new Date());

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);

        log.debug("DODO: Total tweets loaded: " + tweetRepository.count());
    }
}
