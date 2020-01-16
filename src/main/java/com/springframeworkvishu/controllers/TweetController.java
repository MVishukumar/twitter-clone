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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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
