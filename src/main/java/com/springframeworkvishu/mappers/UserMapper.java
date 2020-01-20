package com.springframeworkvishu.mappers;

import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User userCommandToUser(UserCommand userCommand);
    UserCommand userToUserCommand(User user);
}
