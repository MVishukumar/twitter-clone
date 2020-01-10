package com.springframeworkvishu.controllers;

import com.springframeworkvishu.command.TweetCommand;
import com.springframeworkvishu.domain.Tweet;
import com.springframeworkvishu.helpers.Welcome;
import com.springframeworkvishu.mappers.TweetMapper;
import com.springframeworkvishu.services.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class TweetController {
    private final TweetService tweetService;
    private final TweetMapper tweetMapper;

    public TweetController(TweetService tweetService, TweetMapper tweetMapper) {
        this.tweetService = tweetService;
        this.tweetMapper = tweetMapper;
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
    public String serveViewAllTweetsPage(Model model) {
        log.debug("DODO: All Tweets Page Controller");

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

        TweetCommand tweetCommandSaved = tweetService.save(command);

        log.debug("DODO: New tweet saved with id:" + tweetCommandSaved.getId());
        //return "tweet/tweets";
        return "redirect:/tweets";
    }

}
