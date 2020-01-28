package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.CommentMapper;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.CommentRepository;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TweetServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    TweetService tweetService;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Transactional
    @Test
    public void atestSavingTweet() throws Exception {
        long initialCount = tweetRepository.count();
        Tweet tweet = new Tweet();
        tweet.setOpinion("Test tweet");

        TweetCommand tweetSaved = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));

        assertEquals(initialCount+1, tweetRepository.count());
        assertEquals(tweetSaved.getOpinion(), "Test tweet");
    }

    @Transactional
    @Test
    public void btestFindAllTweets() throws Exception {
        Long beforeCount = Long.valueOf(tweetService.findAllTweets().size());

        Tweet t1 = new Tweet();
        t1.setOpinion("First Tweet");

        Tweet t2 = new Tweet();
        t2.setOpinion("Second Tweet");

        tweetService.save(tweetMapper.tweetToTweetCommand(t1));
        tweetService.save(tweetMapper.tweetToTweetCommand(t2));

        Long afterCount = Long.valueOf(tweetService.findAllTweets().size());

        Set<TweetCommand> tweetCommands = tweetService.findAllTweets();

        assertEquals(2, afterCount-beforeCount);
        assertTrue(tweetCommands.size() > 0);

    }

    @Transactional
    @Test
    public void ctestFindTweetById() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setOpinion("Test tweet");

        TweetCommand tweetSaved = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));

        assertNotNull(tweetService.findTweetById(tweetSaved.getId()));
        assertEquals(tweetService.findTweetById(tweetSaved.getId()), tweetSaved);
    }




    @Transactional
    @Test
    public void dtestUpdateTweet() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setOpinion("Test tweet");

        TweetCommand tweetSaved = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));

        TweetCommand newTweet = new TweetCommand();
        newTweet.setOpinion("Test tweet edited");

        TweetCommand tweetSavedEdited = tweetService.editTweet(tweetSaved.getId(), newTweet);

        assertEquals(tweetSavedEdited.getOpinion(), "Test tweet edited");

    }

    @Transactional
    @Test
    public void etestDeleteTweet() throws Exception {
        Long initialCount = Long.valueOf(tweetService.findAllTweets().size());

        Tweet tweet = new Tweet();
        tweet.setOpinion("Test tweet");


        TweetCommand savedTweet = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));

        tweetService.deleteTweet(savedTweet.getId());

        Long finalCount = Long.valueOf(tweetService.findAllTweets().size());

        assertEquals(initialCount, finalCount);
    }


}