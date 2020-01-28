package com.springframeworkvishu.controllers;

import com.springframeworkvishu.command.CommentCommand;
import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.helpers.Welcome;
import com.springframeworkvishu.mappers.CommentMapper;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.services.CommentService;
import com.springframeworkvishu.services.TweetService;
import com.springframeworkvishu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Controller
public class TweetController {
    private final TweetService tweetService;
    private final TweetMapper tweetMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public TweetController(TweetService tweetService, TweetMapper tweetMapper, UserService userService, UserMapper userMapper, CommentService commentService, CommentMapper commentMapper) {
        this.tweetService = tweetService;
        this.tweetMapper = tweetMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @RequestMapping({"", "/", "/index"})
    public String serveIndexPage(Model model) {
        log.debug("DODO: Index Page Controller");
        Welcome welcome = new Welcome();
        welcome.setMessage("Welcome to Twitter.\nThis page needs to be modified as login page");

        model.addAttribute("welcome", welcome);

        return "tweet/welcome";
    }

    @RequestMapping("/tweets")
    public String serveViewAllTweetsPage(Model model,
                                         HttpSession session,
                                         HttpServletRequest request,
                                         @ModelAttribute("useremail") String emailId) {
        log.debug("DODO: All Tweets Page Controller");

        log.debug("DODO: Object passed from redirection: " + emailId);

        AtomicReference<Boolean> readFromSession = new AtomicReference<>(false);
        AtomicReference<String> email = new AtomicReference<>();

        //Check logged in user
        List<String> loggedInUsers = (List<String>) session.getAttribute("LOGGED_IN_USER");
        if (loggedInUsers == null) {
            log.debug("DODO: No logged in user. Email attribute passed: " + emailId);
            if ("".equalsIgnoreCase(emailId) || emailId == null) {
                log.debug("DODO: No flash attribute as well for email, should be redirected to login page.");
                UserCommand userCommand = new UserCommand();
                model.addAttribute("usercommand", userCommand);
                return "login";
            } else {
                log.debug("DODO: No email attribute passed.");
                UserCommand command = userService.findByEmail(emailId);
                loggedInUsers = new ArrayList<>();
                loggedInUsers.add(command.getEmail());
                request.getSession().setAttribute("LOGGED_IN_USER", loggedInUsers);
            }
        } else {
            log.debug("DODO: User logged in:");
            log.debug("DODO: Active users:" + loggedInUsers.size());
            loggedInUsers.forEach(userEmail -> {
                log.debug("DODO: " + userEmail);
                readFromSession.set(true);
                log.debug("DODO: Read from session is set to true");
                email.set(userEmail);
                log.debug("DODO: Email set:" + email.get());
            });


            UserCommand userCommand = userService.findByEmail(email.get());
            model.addAttribute("loggedinuser", userCommand);
            log.debug("DODO: " + userCommand);
        }

        //For new tweet form
        TweetCommand tweetCommand = new TweetCommand();
        model.addAttribute("tweetcommand", tweetCommand);


        if (readFromSession.get() == false) {
            log.debug("DODO: User session closed, reading from model attribute.");
            UserCommand loggedInUser = userService.findByEmail(emailId);
            model.addAttribute("loggedinuser", loggedInUser);
        }

        //For displaying all tweets
        Set<TweetCommand> tweetCommands = getAllTweetCommands();
        model.addAttribute("alltweets", tweetCommands);

        return "tweet/tweets";
    }

    private Set<TweetCommand> getAllTweetCommands() {
        Set<TweetCommand> tweetCommands = new HashSet<>();
        tweetCommands = tweetService.findAllTweets();
        return tweetCommands;
    }

    @RequestMapping("/new")
    public String serveNewTweetPage(Model model) {
        log.debug("DODO: New Tweet Page Controller");
        TweetCommand tweetCommand = new TweetCommand();

        model.addAttribute("tweetcommand", tweetCommand);

        return "tweet/new";
    }

    @PostMapping("/new")
    public String saveOrUpdate(@Valid @ModelAttribute("tweetcommand") TweetCommand command,
                               BindingResult bindingResult,
                               HttpServletRequest request) {
        log.debug("DODO: Saving New Tweet Controller");
        AtomicReference<String> email = new AtomicReference<>();

        if (bindingResult.hasErrors()) {
            log.debug("DODO: Error saving new tweet");
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "tweet/tweets";
        }

        //Check user session
        List<String> loggedInUsers = (List<String>) request.getSession().getAttribute("LOGGED_IN_USER");
        if (loggedInUsers == null) {
            log.debug("DODO: User not logged in, redirecting to login page");
            return "redirect:/login";
        }


        loggedInUsers.forEach(userEmail -> {
            email.set(userEmail);
        });

        log.debug("DODO: Logged in users: " + loggedInUsers);
        log.debug("DODO: Logged in user email: " + email.get());
        UserCommand userCommand = userService.findByEmail(email.get());

        command.setDate(new Date());
        //command.setUser(userMapper.userCommandToUser(userCommand));
        TweetCommand tweetCommandSaved = tweetService.save(command, userCommand);

        log.debug("DODO: New tweet saved with id:" + tweetCommandSaved.getId());
        //return "tweet/tweets";
        return "redirect:/tweets";
    }

    @GetMapping("/edit/{id}")
    public String saveOrUpdate(@PathVariable String id, Model model) {
        log.debug("DODO: Editing existing tweet with id: " + id + " Controller");

        TweetCommand tweetCommand = new TweetCommand();
        tweetCommand.setId(new Long(id));

        TweetCommand existingTweet = tweetService.findTweetById(new Long(id));
        tweetCommand.setOpinion(existingTweet.getOpinion());

        model.addAttribute("tweetcommand", tweetCommand);

        return "tweet/edit";
    }

    @PostMapping("/edit/{id}")
    public String saveOrUpdate(@PathVariable String id, @Valid @ModelAttribute("tweetcommand") TweetCommand command, BindingResult bindingResult) {
        log.debug("DODO: Editing existing tweet with id: " + id + " in Post Controller");

        TweetCommand tweetExisting = tweetService.findTweetById(new Long(id));
        tweetExisting.setOpinion(command.getOpinion());

        //tweetService.save(tweetExisting);

        return "redirect:/tweets";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        log.debug("DODO: Deleting existing tweet with id: " + id + " Controller");

        tweetService.deleteTweet(new Long(id));

        return "redirect:/tweets";
    }

    @GetMapping("/tweets/{id}")
    public String serveTweetDetailsPage(@PathVariable String id, Model model,
                                        HttpServletRequest request) {
        log.debug("DODO: Reading existing tweet with id: " + id + " Controller");

        TweetCommand command = tweetService.findTweetById(Long.valueOf(id));

        model.addAttribute("command", command);

        String loggedInUserEmail = getLoggedInUser(request);

        CommentCommand commentCommand = new CommentCommand();
        model.addAttribute("commentcommand", commentCommand);

        log.debug("DODO: Logged in user: " + loggedInUserEmail);
        return "/tweet/details";
    }

    private String getLoggedInUser(HttpServletRequest request) {
        AtomicReference<String> email = new AtomicReference<>();

        List<String> loggedInUsers = (List<String>) request.getSession().getAttribute("LOGGED_IN_USER");
        if (loggedInUsers == null) {
            log.debug("DODO: User not logged in, redirecting to login page");
            return "redirect:/login";
        }


        loggedInUsers.forEach(userEmail -> {
            email.set(userEmail);
        });

        return String.valueOf(email);
    }

    @PostMapping("/tweet/{id}/comment")
    public String saveOrUpdate(@PathVariable String id, @Valid @ModelAttribute("commentcommand") CommentCommand commentCommand
            , BindingResult bindingResult
            , HttpServletRequest request) {
        log.debug("DODO: Comment received: " + commentCommand.getCommentDescription());

        TweetCommand tweetCommand = tweetService.findTweetById(Long.valueOf(id));
        String userEmail = getLoggedInUser(request);
        UserCommand userCommand = userService.findByEmail(userEmail);

        log.debug("DODO: Tweet: " + tweetCommand);
        log.debug("DODO: Logged In Email: " + userEmail);
        log.debug("DODO: User: " + userCommand);

        CommentCommand commentSaved = commentService.createNewComment(commentCommand, userCommand, tweetCommand);

        log.debug("DODO: New comment saved with ID: " + commentSaved.getId());
        return "redirect:/";
    }
}
