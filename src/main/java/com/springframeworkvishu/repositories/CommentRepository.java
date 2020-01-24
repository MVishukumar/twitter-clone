package com.springframeworkvishu.repositories;

import com.springframeworkvishu.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll(Sort date);
}
