package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.Tweet;

import java.util.Set;


public interface TweetService {

    Set<TweetCommand> findAllTweets();

    TweetCommand save(TweetCommand tweet, UserCommand userCommand);

    void deleteTweet(Long id);

    TweetCommand editTweet(Long id, TweetCommand tweetCommand);

    TweetCommand findTweetById(Long id);

}
