package com.springframeworkvishu.services;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;

import java.util.Set;

public interface CommentService {
    Set<CommentCommand> findAllComments();
    CommentCommand createNewComment(CommentCommand commentCommand,
                                    UserCommand userCommand,
                                    TweetCommand tweetCommand);
    void deleteComment(Long id);
}
