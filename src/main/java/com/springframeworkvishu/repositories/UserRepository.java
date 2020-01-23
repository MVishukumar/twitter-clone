package com.springframeworkvishu.repositories;

import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
