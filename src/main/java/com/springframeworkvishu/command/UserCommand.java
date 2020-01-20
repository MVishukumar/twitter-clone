package com.springframeworkvishu.command;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommand {
    private Long id;
    private String username;
    private String password;
}
