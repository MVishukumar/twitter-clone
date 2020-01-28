package com.springframeworkvishu.services;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public TweetServiceImpl(TweetRepository tweetRepository, TweetMapper tweetMapper, UserRepository userRepository, UserMapper userMapper) {
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
    @Transactional
    public TweetCommand save(TweetCommand tweet, UserCommand userCommand) {
        log.debug("DODO: Save Tweet Service");

        Tweet tweet1 = tweetMapper.tweetCommandToTweet(tweet);
        User user = userMapper.userCommandToUser(userCommand);

        tweet1.setUser(user);
        user.getTweets().add(tweet1);

        TweetCommand savedTweet = tweetMapper.tweetToTweetCommand(
                tweetRepository.save(tweet1)
        );

        return savedTweet;
    }

    @Override
    @Transactional
    public TweetCommand save(TweetCommand tweet) {
        log.debug("DODO: Save Tweet Service");

        Tweet tweetSaved = tweetRepository.save(tweetMapper.tweetCommandToTweet(tweet));

        return tweetMapper.tweetToTweetCommand(tweetSaved);
    }

    @Override
    public void deleteTweet(Long id) {
        log.debug("DODO: Delete Tweet Service");
        tweetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TweetCommand editTweet(Long id, TweetCommand tweetCommand) {
        log.debug("DODO: Edit Tweet Service");
        Tweet tweetFromDb = tweetRepository.findById(id).get();

        tweetFromDb.setOpinion(tweetCommand.getOpinion());

        Tweet savedTweet = tweetRepository.save(tweetFromDb);

        return tweetMapper.tweetToTweetCommand(savedTweet);
    }

    @Override
    @Transactional
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
