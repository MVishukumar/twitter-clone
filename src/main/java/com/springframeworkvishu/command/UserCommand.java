package com.springframeworkvishu.command;

import com.springframeworkvishu.domain.Tweet;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommand {
    private Long id;
    private String username;
    private String password;
    private String email;
    Set<Tweet> tweets = new HashSet<>();
}
