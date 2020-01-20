package com.springframeworkvishu.repositories;

import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    UserCommand findByUsername(String username);
}
