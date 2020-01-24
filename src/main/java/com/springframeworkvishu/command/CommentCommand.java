package com.springframeworkvishu.command;

import lombok.*;

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
}
