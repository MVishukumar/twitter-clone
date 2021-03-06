package com.springframeworkvishu.controllers;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.domain.User;
import com.springframeworkvishu.helpers.Welcome;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.services.TweetService;
import com.springframeworkvishu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class LoginLogoutController {
    private final TweetService tweetService;
    private final TweetMapper tweetMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public LoginLogoutController(TweetService tweetService, TweetMapper tweetMapper, UserService userService, UserMapper userMapper) {
        this.tweetService = tweetService;
        this.tweetMapper = tweetMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public String saveOrUpdate(@Valid @ModelAttribute("usercommand") UserCommand command,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){
        log.debug("DODO: Login controller Controller");
        if(bindingResult.hasErrors()){
            log.debug("DODO: Error in login controller");
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "login";
        }

        List<String> loggedInUsers = (List<String>) request.getSession().getAttribute("LOGGED_IN_USER");
        if (loggedInUsers == null) {
            loggedInUsers = new ArrayList<>();
            request.getSession().setAttribute("LOGGED_IN_USER", loggedInUsers);
        }
        loggedInUsers.add(command.getEmail());
        request.getSession().setAttribute("LOGGED_IN_USER", loggedInUsers);

        log.debug("DODO: New session attribute with email id:" + command.getEmail() + " is added");

        //Save the user command
        UserCommand savedUserCommand = userService.createNewUser(command);
        log.debug("DODO: New user with id: " + savedUserCommand.getId() + " saved to database");

        redirectAttributes.addFlashAttribute("useremail", savedUserCommand.getEmail());

        return "redirect:/tweets";
    }

    @PostMapping("/loginexisting")
    public String validateLogin(@Valid @ModelAttribute("usercommand") UserCommand command,
                                BindingResult bindingResult,
                                HttpServletRequest request,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        log.debug("DODO: Login existing user controller Controller");
        String existingUserEmailId = command.getEmail();

        UserCommand userCommand = userService.findByEmail(existingUserEmailId);
        if(userCommand == null) {
            log.debug("DODO: User " + command.getEmail() + " does not existing, redirecting to signup/login page");
            return "redirect:/login";
        } else {
            log.debug("DODO: User " + command.getEmail() + " found in db. Log in success");
            redirectAttributes.addFlashAttribute("useremail", command.getEmail());
            return "redirect:/tweets";
        }
    }


    @PostMapping("/logout")
    public String logout(@Valid @ModelAttribute("loggedinuser") UserCommand command,
                         BindingResult bindingResult,
                         HttpServletRequest request) {
        log.debug("DODO: Logout user Controller");
        log.debug("DODO: About to log out user: " + command.getEmail());

        request.getSession().invalidate();

        return "redirect:/";
    }

}
