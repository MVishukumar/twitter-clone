package com.springframeworkvishu.services;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;

import java.util.Set;

public interface CommentService {
    Set<CommentCommand> findAllComments();
    CommentCommand createNewComment(CommentCommand commentCommand);
    void deleteComment(Long id);
}
