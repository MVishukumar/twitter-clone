package com.springframeworkvishu.command;

import com.springframeworkvishu.domain.Comment;
import com.springframeworkvishu.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetCommand {
    private Long id;
    private String opinion;
    private Date date;
    private User user;
    Set<Comment> comments = new HashSet<>();
}