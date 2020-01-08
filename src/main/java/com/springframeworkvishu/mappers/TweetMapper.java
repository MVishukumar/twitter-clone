package com.springframeworkvishu.mappers;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TweetMapper {
    TweetMapper TWEET_MAPPER = Mappers.getMapper(TweetMapper.class);

    Tweet tweetCommandToTweet(TweetCommand tweetCommand);
    TweetCommand tweetToTweetCommand(Tweet tweet);
}