package com.springframeworkvishu.command;

import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCommand {
    private Long id;
    private String commentDescription;
    private Date date;
    private Tweet tweet;
    private User user;
}
