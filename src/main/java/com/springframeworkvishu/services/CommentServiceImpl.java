package com.springframeworkvishu.services;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.mappers.CommentMapper;
import com.springframeworkvishu.repositories.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Set<CommentCommand> findAllComments() {
        log.debug("DODO: Find All Comment Service");
        Set<CommentCommand> commentCommands = new HashSet<>();

        commentRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))
                .iterator()
                .forEachRemaining(e -> commentCommands.add(commentMapper.commentToCommentCommand(e)));


        return commentCommands;
    }

    @Override
    public CommentCommand createNewComment(CommentCommand commentCommand) {
        log.debug("DODO: Create New Comment Service");

        Comment comment = commentMapper.commentCommandToComment(commentCommand);

        Comment commentSaved = commentRepository.save(comment);

        return commentMapper.commentToCommentCommand(commentSaved);
    }

    @Override
    public void deleteComment(Long id) {
        log.debug("DODO: Delete Comment Service");

        commentRepository.deleteById(id);
    }

}
