package com.springframeworkvishu.mappers;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    Comment commentCommandToComment(CommentCommand commentCommand);
    CommentCommand commentToCommentCommand(Comment comment);
}