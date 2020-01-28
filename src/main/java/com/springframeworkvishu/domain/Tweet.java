package com.springframeworkvishu.domain;

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
@Entity
@EqualsAndHashCode(exclude = {"user","comments"})
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String opinion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    @ToString.Exclude
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setTweet(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setTweet(null);
    }
}
