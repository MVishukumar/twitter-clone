package com.springframeworkvishu.command;

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
}