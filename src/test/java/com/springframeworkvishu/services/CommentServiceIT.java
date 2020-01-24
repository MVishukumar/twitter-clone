package com.springframeworkvishu.services;


import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.mappers.CommentMapper;
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

    @Transactional
    @Test
    public void aTestSaveNewComment() throws Exception {
        Comment comment = new Comment();
        comment.setCommentDescription(COMMENT_DESC);

        CommentCommand commentSaved = commentService.createNewComment(commentMapper.commentToCommentCommand(comment));

        assertEquals(new Long(1L), commentSaved.getId()); //2 tweet objects will be saved during init
        assertEquals(COMMENT_DESC, commentSaved.getCommentDescription());
    }

    @Transactional
    @Test
    public void bTestFindAllComments() throws Exception {
        Comment comment1 = new Comment();
        comment1.setCommentDescription(COMMENT_DESC);

        Comment comment2 = new Comment();
        comment2.setCommentDescription(COMMENT_DESC);

        CommentCommand savedComment1 = commentService.createNewComment(commentMapper.commentToCommentCommand(comment1));
        CommentCommand savedComment2 = commentService.createNewComment(commentMapper.commentToCommentCommand(comment2));

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
