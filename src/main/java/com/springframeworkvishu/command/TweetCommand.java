package com.springframeworkvishu.command;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetCommand {
    private Long id;
    private String opinion;
}