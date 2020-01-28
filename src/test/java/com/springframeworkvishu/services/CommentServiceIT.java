package com.springframeworkvishu.services;


import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.CommentMapper;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.CommentRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentServiceIT {
    private static final String COMMENT_DESC = "This is comment";

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TweetService tweetService;

    @Autowired
    TweetMapper tweetMapper;

    @Transactional
    @Test
    public void aTestSaveNewComment() throws Exception {
        Comment comment = new Comment();
        comment.setCommentDescription(COMMENT_DESC);

        User user = new User();
        user.setUsername("azo");


        Tweet tweet = new Tweet();
        tweet.setOpinion("Tweet from azo");

        TweetCommand tweetSaved = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));
        UserCommand userSaved = userService.createNewUser(userMapper.userToUserCommand(user));
        tweetSaved.setUser(userMapper.userCommandToUser(userSaved));
        userSaved.getTweets().add(tweetMapper.tweetCommandToTweet(tweetSaved));
        TweetCommand tweetSaved2 = tweetService.save(tweetMapper.tweetToTweetCommand(tweet));
        UserCommand userSaved2 = userService.createNewUser(userMapper.userToUserCommand(user));


        CommentCommand commentSaved = commentService.createNewComment(commentMapper.commentToCommentCommand(comment),
                userSaved, tweetSaved);

        assertTrue(commentSaved.getId() > 0);
        assertEquals(COMMENT_DESC, commentSaved.getCommentDescription());
        assertEquals(commentSaved.getUser().getId(), userSaved.getId());
        assertEquals(commentSaved.getTweet().getOpinion(), tweetSaved.getOpinion());
        assertEquals(commentSaved.getTweet().getId(), tweetSaved.getId());

    }

    @Transactional
    @Test
    public void bTestFindAllComments() throws Exception {
        Comment comment1 = new Comment();
        comment1.setCommentDescription(COMMENT_DESC);

        Comment comment2 = new Comment();
        comment2.setCommentDescription(COMMENT_DESC);

        //CommentCommand savedComment1 = commentService.createNewComment(commentMapper.commentToCommentCommand(comment1));
        //CommentCommand savedComment2 = commentService.createNewComment(commentMapper.commentToCommentCommand(comment2));

        Set<CommentCommand> allComments = new HashSet<>();

        allComments = commentService.findAllComments();

        assertEquals(3, allComments.size());
    }

    @Transactional
    @Test
    public void cTestDeleteComment() throws Exception {
        commentService.deleteComment(Long.valueOf(1L));
        commentService.deleteComment(Long.valueOf(2L));

        assertEquals(1, commentService.findAllComments().size());

    }
}
