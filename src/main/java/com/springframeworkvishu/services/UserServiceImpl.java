package com.springframeworkvishu.services;

import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;

    public UserServiceImpl(UserRepository userRepository, TweetRepository tweetRepository, UserMapper userMapper, TweetMapper tweetMapper) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.userMapper = userMapper;
        this.tweetMapper = tweetMapper;
    }

    @Override
    public UserCommand createNewUser(UserCommand userCommand) {
        log.debug("DODO: User, create new service");
        User userSaved = userRepository.save(userMapper.userCommandToUser(userCommand));

        return userMapper.userToUserCommand(userSaved);
    }

    @Override
    public UserCommand editPassword(Long id, String newPassword) {
        log.debug("DODO: User, edit password service");
        Optional<User> optUserFromDb = userRepository.findById(id);
        User userFromDb = null;
        if(optUserFromDb.isPresent()) {
            userFromDb = optUserFromDb.get();

            userFromDb.setPassword(newPassword);

            UserCommand userCommand = createNewUser(userMapper.userToUserCommand(userFromDb));

            return userCommand;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("DODO: User, delete service");
        userRepository.delete(userRepository.findById(id).get());
    }

    @Override
    public UserCommand findById(Long id) {
        log.debug("DODO: User, find by id service");

        User user = userRepository.findById(id).get();

        return userMapper.userToUserCommand(user);
    }
}
