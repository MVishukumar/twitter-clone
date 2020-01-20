package com.springframeworkvishu.services;

import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    private final String NEW_USERNAME = "BOB";
    private final String NEW_PASSWORD = "BOBPASSWORD";
    private final String NEW_PASSWORD_EDITED = "BOBPASSWORDEDITED";

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Transactional
    @Test
    public void createNewUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername(NEW_USERNAME);
        newUser.setPassword(NEW_PASSWORD);

        UserCommand userSaved = userService.createNewUser(userMapper.userToUserCommand(newUser));

        assertEquals(newUser.getId(), userSaved.getId());
        assertEquals(NEW_USERNAME, userSaved.getUsername());
        assertEquals(NEW_PASSWORD, userSaved.getPassword());
    }

    @Transactional
    @Test
    public void editPassword() {
        User user = new User();
        user.setId(1L);
        user.setPassword(NEW_PASSWORD);
        user.setUsername(NEW_USERNAME);

        UserCommand userSaved = userService.createNewUser(userMapper.userToUserCommand(user));

        UserCommand userFromDb = userService.findById(new Long(1L));
        userFromDb.setPassword(NEW_PASSWORD_EDITED);

        UserCommand editedUserSaved = userService.createNewUser(userFromDb);

        assertEquals(userSaved.getId(), editedUserSaved.getId());
        assertEquals(userSaved.getUsername(), editedUserSaved.getUsername());
        assertNotEquals(userSaved.getPassword(), editedUserSaved.getPassword());
        assertEquals(userSaved.getPassword(), NEW_PASSWORD);
        assertEquals(editedUserSaved.getPassword(), NEW_PASSWORD_EDITED);
    }

    @Transactional
    @Test
    public void deleteUser() {
        createNewUser();

        assertEquals(userRepository.count(), 1);

        userService.deleteUser(1L);

        assertEquals(userRepository.count(), 0);
    }

    @Transactional
    @Test
    public void findById() {
        createNewUser();

        UserCommand userCommand = userService.findById(new Long(1L));

        assertEquals(userCommand.getId(), new Long(1L));
        assertEquals(userCommand.getUsername(), NEW_USERNAME);
        assertEquals(userCommand.getPassword(), NEW_PASSWORD);
    }

    @Transactional
    @Test
    public void findByUsername() {
        createNewUser();

        UserCommand userCommand = userService.findByUsername(NEW_USERNAME);

        assertEquals(userCommand.getId(), new Long(1L));
        assertEquals(userCommand.getUsername(), NEW_USERNAME);
        assertEquals(userCommand.getPassword(), NEW_PASSWORD);
    }
}