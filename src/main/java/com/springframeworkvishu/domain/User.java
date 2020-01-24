package com.springframeworkvishu.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"tweets"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    Set<Tweet> tweets = new HashSet<>();
}
