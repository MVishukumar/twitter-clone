package com.springframeworkvishu.controllers;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.command.UserCommand;
import com.springframeworkvishu.helpers.Welcome;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.mappers.UserMapper;
import com.springframeworkvishu.services.TweetService;
import com.springframeworkvishu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class TweetController {
    private final TweetService tweetService;
    private final TweetMapper tweetMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public TweetController(TweetService tweetService, TweetMapper tweetMapper, UserService userService, UserMapper userMapper) {
        this.tweetService = tweetService;
        this.tweetMapper = tweetMapper;
        this.userService = userService;
        this.userMapper = userMapper;
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
                                         @ModelAttribute("useremail") String emailId) {
        log.debug("DODO: All Tweets Page Controller");

        log.debug("DODO: Object passed from redirection: " + emailId);

        //Check logged in user
        List<String> loggedInUsers = (List<String>) session.getAttribute("LOGGED_IN_USER");
        if(loggedInUsers == null) {
            log.debug("DODO: No logged in user, should be redirected to Login Page");
            UserCommand userCommand = new UserCommand();
            model.addAttribute("usercommand", userCommand);
            return "login";
        } else {
            log.debug("DODO: User logged in:");
            loggedInUsers.forEach(userName -> {
                System.out.println(userName);
            } );
        }

        //For new tweet form
        TweetCommand tweetCommand = new TweetCommand();
        model.addAttribute("tweetcommand", tweetCommand);

        UserCommand loggedInUser = userService.findByEmail(emailId);
        model.addAttribute("loggedinuser", loggedInUser);

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
    public String saveOrUpdate(@Valid @ModelAttribute("tweetcommand") TweetCommand command, BindingResult bindingResult){
        log.debug("DODO: Saving New Tweet Controller");
        if(bindingResult.hasErrors()){
            log.debug("DODO: Error saving new tweet");
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "tweet/tweets";
        }
        command.setDate(new Date());
        TweetCommand tweetCommandSaved = tweetService.save(command);

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

        tweetService.save(tweetExisting);

        return "redirect:/tweets";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        log.debug("DODO: Deleting existing tweet with id: " + id + " Controller");

        tweetService.deleteTweet(new Long(id));

        return "redirect:/tweets";
    }

    @GetMapping("/tweets/{id}")
    public String serveTweetDetailsPage(@PathVariable String id, Model model) {
        log.debug("DODO: Reading existing tweet with id: " + id + " Controller");

        TweetCommand command = tweetService.findTweetById(Long.valueOf(id));

        model.addAttribute("command", command);

        return "/tweet/details";
    }

}
