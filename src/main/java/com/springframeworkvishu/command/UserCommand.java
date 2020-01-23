package com.springframeworkvishu.command;

import com.springframeworkvishu.domain.Tweet;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
