package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.repositories.TweetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    TweetService tweetService;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    TweetMapper tweetMapper;

    @Transactional
    @Test
    public void testSavingTweet() throws Exception {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setOpinion("Test tweet");

        Tweet savedTweet = tweetMapper.tweetCommandToTweet(tweetService.save(tweetMapper.tweetToTweetCommand(tweet)));

        assertEquals(new Long(1L), savedTweet.getId());
        assertEquals(new String("Test tweet"), savedTweet.getOpinion());
    }

    @Transactional
    @Test
    public void testFindAllTweets() throws Exception {
        Tweet t1 = new Tweet();
        t1.setOpinion("tweet 1");

        Tweet t2 = new Tweet();
        t2.setOpinion("tweet 2");

        tweetRepository.save(t1);
        tweetRepository.save(t2);

        Set<Tweet> allTweets = new HashSet<>();

        tweetService.findAllTweets().iterator().forEachRemaining(e -> allTweets.add(tweetMapper.tweetCommandToTweet(e)));

        assertEquals(allTweets.size(), tweetRepository.count());
        assertEquals(4, tweetRepository.count()); //4 because 2 will be saved during intial data load
    }

    @Transactional
    @Test
    public void testDeleteTweet() throws Exception {
        tweetService.deleteTweet(1L);

        assertEquals(1, tweetRepository.count()); //1 because 2 will be saved during initial data load

    }


    @Transactional
    @Test
    public void testUpdateTweet() throws Exception {
        TweetCommand tweetCommand = new TweetCommand();
        tweetCommand.setOpinion("This is edited");

        TweetCommand tweetCommandSaved = tweetService.editTweet(1L, tweetCommand);

        Tweet tweetFromDb = tweetRepository.findById(1L).get();
        assertEquals(tweetFromDb.getOpinion(), new String("This is edited"));
        assertEquals(tweetFromDb.getOpinion(), tweetCommandSaved.getOpinion());

    }


}