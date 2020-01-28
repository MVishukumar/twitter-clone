package com.springframeworkvishu.bootstrap;

import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.repositories.CommentRepository;
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
    private final CommentRepository commentRepository;

    public Bootstrap(TweetRepository tweetRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("DODO: Loading tweets");
        loadInitialUsers();
        loadInitialTweets();
        loadInitialComments();
    }

    private void loadInitialComments() {
        log.debug("DODO: Loading comments");
        Tweet tweet = tweetRepository.findById(1L).get();


        Comment comment = new Comment();
        comment.setCommentDescription("First comment");
        comment.setDate(new Date());
        comment.setTweet(tweet);
        comment.setUser(tweet.getUser());
        commentRepository.save(comment);

        tweet.getComments().add(comment);

        tweetRepository.save(tweet);

        log.debug("DODO: Comments saved: " + commentRepository.count());
    }

    private void loadInitialUsers() {
        log.debug("DODO: Loading initial users");
        User user1 = new User();
        user1.setUsername("roxanne");
        user1.setPassword("roxanne");
        user1.setEmail("roxanne@example.com");

        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("rosy");
        user2.setPassword("rosy");
        user2.setEmail("rosy@example.com");

        userRepository.save(user2);
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

        User user1 = userRepository.findByUsername("roxanne");
        User user2 = userRepository.findByUsername("rosy");

        tweet1.setUser(user1);
        tweet2.setUser(user2);

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);

        log.debug("DODO: Total tweets loaded: " + tweetRepository.count());
    }
}
