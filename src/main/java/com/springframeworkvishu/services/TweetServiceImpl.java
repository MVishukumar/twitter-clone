package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    public TweetServiceImpl(TweetRepository tweetRepository, TweetMapper tweetMapper) {
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
    }


    @Override
    public Set<TweetCommand> findAllTweets() {
        log.debug("DODO: Find All Tweet Service");
        Set<TweetCommand> tweetCommands = new HashSet<>();

        tweetRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))
                .iterator()
                .forEachRemaining(e -> tweetCommands.add(tweetMapper.tweetToTweetCommand(e)));

        return tweetCommands;
    }

    @Override
    public TweetCommand save(TweetCommand tweet) {
        log.debug("DODO: Save Tweet Service");

        TweetCommand savedTweet = tweetMapper.tweetToTweetCommand(
                tweetRepository.save(tweetMapper.tweetCommandToTweet(tweet))
        );

        return savedTweet;
    }

    @Override
    public void deleteTweet(Long id) {
        log.debug("DODO: Delete Tweet Service");
        tweetRepository.deleteById(id);
    }

    @Override
    public TweetCommand editTweet(Long id, TweetCommand tweetCommand) {
        log.debug("DODO: Edit Tweet Service");
        Tweet tweetFromDb = tweetRepository.findById(id).get();

        tweetFromDb.setOpinion(tweetCommand.getOpinion());

        Tweet savedTweet = tweetRepository.save(tweetFromDb);

        return tweetMapper.tweetToTweetCommand(savedTweet);
    }

    @Override
    public TweetCommand findTweetById(Long id) {
        log.debug("DODO: Find Tweet By Id Service");

        Optional<Tweet> tweet = tweetRepository.findById(new Long(id));

        if(tweet.isPresent()) {
            return tweetMapper.tweetToTweetCommand(tweet.get());
        } else {
            return null;
        }
    }
}
