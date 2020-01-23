package com.springframeworkvishu.command;

import com.springframeworkvishu.domain.User;
import lombok.*;

import java.util.Date;

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
}