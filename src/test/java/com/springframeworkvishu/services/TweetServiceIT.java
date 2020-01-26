package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


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

    @Transactional
    @Test
    public void atestSavingTweet() throws Exception {
        Tweet tweet = new Tweet();
        //tweet.setId(1L);
        tweet.setOpinion("Test tweet");

        User user = new User();
        user.setEmail("example@example.com");

        Tweet savedTweet = tweetMapper.tweetCommandToTweet(tweetService.save(tweetMapper.tweetToTweetCommand(tweet),
                userMapper.userToUserCommand(user)));

        assertEquals(new Long(3L), savedTweet.getId()); //2 tweet objects will be saved during init
        assertEquals(new String("Test tweet"), savedTweet.getOpinion());
    }

    @Transactional
    @Test
    public void btestFindAllTweets() throws Exception {
        Tweet tweet = new Tweet();
        //tweet.setId(1L);
        tweet.setOpinion("Test tweet");
        tweet.setDate(new Date());

        User user = userRepository.findByUsername("roxanne");
        tweet.setUser(user);

        Tweet savedTweet = tweetMapper.tweetCommandToTweet(tweetService.save(tweetMapper.tweetToTweetCommand(tweet),
                userMapper.userToUserCommand(user)));

        Set<Tweet> allTweets = new HashSet<>();

        tweetService.findAllTweets().iterator().forEachRemaining(e -> allTweets.add(tweetMapper.tweetCommandToTweet(e)));

        assertEquals(allTweets.size(), tweetRepository.count());
        assertEquals(4, tweetRepository.count()); //4 because 2 will be saved during intial data load
    }

    @Transactional
    @Test
    public void ctestFindTweetById() throws Exception {
        Tweet t1 = new Tweet();
        t1.setId(3L);
        t1.setOpinion("tweet 1");
        t1.setDate(new Date());

        User user = userRepository.findByUsername("roxanne");

        t1.setUser(user);
        tweetRepository.save(t1);


        Tweet tweetFromDb = tweetRepository.findById(3L).get();

        assertEquals(tweetFromDb.getId(), t1.getId());
        assertEquals(tweetFromDb.getOpinion(), t1.getOpinion());
    }




    @Transactional
    @Test
    public void dtestUpdateTweet() throws Exception {
        //testSavingTweet();

        TweetCommand tweetCommand = new TweetCommand();
        tweetCommand.setOpinion("This is edited");

        TweetCommand tweetCommandSaved = tweetService.editTweet(2L, tweetCommand);

        //Tweet tweetFromDb = tweetRepository.findById(1L).get();
        TweetCommand tweetFromDb = tweetService.findTweetById(2L);
        assertEquals(tweetFromDb.getOpinion(), new String("This is edited"));
        assertEquals(tweetFromDb.getOpinion(), tweetCommandSaved.getOpinion());

    }

    @Transactional
    @Test
    public void etestDeleteTweet() throws Exception {
        tweetService.deleteTweet(1L);

        assertEquals(3, tweetRepository.count()); //1 because 2 will be saved during initial data load

    }


}