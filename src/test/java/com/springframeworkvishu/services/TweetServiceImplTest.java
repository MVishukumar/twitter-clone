package com.springframeworkvishu.services;

import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TweetServiceImplTest {

    TweetService tweetService;

    @Mock
    TweetRepository tweetRepository;

    @Mock
    TweetMapper tweetMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        tweetService = new TweetServiceImpl(tweetRepository, tweetMapper, userRepository, userMapper);
    }

    @Test
    public void findAllTweets() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        Optional<Tweet> optionalTweet = Optional.of(tweet);

        when(tweetRepository.findById(anyLong())).thenReturn(optionalTweet);

        Tweet tweetReturned = tweetRepository.findById(1L).get();

        assertNotNull("Null tweet returned", tweetReturned);
        verify(tweetRepository, times(1)).findById(anyLong());
        verify(tweetRepository, never()).findAll();

    }
}