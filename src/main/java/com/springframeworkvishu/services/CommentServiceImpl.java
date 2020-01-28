package com.springframeworkvishu.services;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.mappers.CommentMapper;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.CommentRepository;
import com.springframeworkvishu.repositories.TweetRepository;
import com.springframeworkvishu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final TweetService tweetService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, TweetRepository tweetRepository, TweetMapper tweetMapper, TweetService tweetService, UserRepository userRepository, UserMapper userMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.tweetService = tweetService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public Set<CommentCommand> findAllComments() {
        log.debug("DODO: Find All Comment Service");
        Set<CommentCommand> commentCommands = new HashSet<>();

        commentRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))
                .iterator()
                .forEachRemaining(e -> commentCommands.add(commentMapper.commentToCommentCommand(e)));


        return commentCommands;
    }

    @Override
    @Transactional
    public CommentCommand createNewComment(CommentCommand commentCommand,
                                           UserCommand userCommand,
                                           TweetCommand tweetCommand) {
        log.debug("DODO: Create New Comment");

        commentCommand.setTweet(tweetMapper.tweetCommandToTweet(tweetCommand));
        commentCommand.setUser(userMapper.userCommandToUser(userCommand));
        commentCommand.setDate(new Date());
        Comment commentSaved = commentRepository.save(commentMapper.commentCommandToComment(commentCommand));

        tweetCommand.getComments().add(commentMapper.commentCommandToComment(
                commentMapper.commentToCommentCommand(commentSaved)));

        TweetCommand tweetSaved = tweetService.save(tweetCommand);

        return commentMapper.commentToCommentCommand(commentSaved);
    }


    @Override
    public void deleteComment(Long id) {
        log.debug("DODO: Delete Comment Service");

        commentRepository.deleteById(id);
    }

}
