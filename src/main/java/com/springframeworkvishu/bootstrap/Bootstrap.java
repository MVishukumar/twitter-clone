package com.springframeworkvishu.bootstrap;

import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TweetRepository tweetRepository;

    public Bootstrap(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("DODO: Loading tweets");
        loadInitialTweets();
    }

    private void loadInitialTweets() {
        log.debug("DODO: Loading initial tweets");
        Tweet tweet1 = new Tweet();
        tweet1.setOpinion("This project is going to be completed");

        Tweet tweet2 = new Tweet();
        tweet2.setOpinion("This project is going to be awesome");

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);

        log.debug("DODO: Total tweets loaded: " + tweetRepository.count());
    }
}
