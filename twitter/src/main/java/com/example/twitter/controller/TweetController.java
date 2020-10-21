package com.example.twitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import com.example.twitter.repository.TweetRepository;
import com.example.twitter.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class TweetController {
	private final static String USER_NOT_FOUND = "User does not exist";

	@Autowired
	TweetRepository tweetRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/tweets")
	public Page<Tweet> getTweets(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber(), 1, Sort.by("updatedAt"));
		return tweetRepository.findAll(pageable);	
	}

	@PostMapping("/tweets/{userId}")
	public Tweet postTweet(@PathVariable (value = "userId") Long userId, @Valid @RequestBody Tweet tweet) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
		tweet.setTweeter(user);
		return tweetRepository.save(tweet);
	}
	
	@GetMapping("/tweets/title/{title}")
	public Tweet getTweetByTitle(@PathVariable(value = "title") String title) {
		return tweetRepository.findByTitle(title);
	}

}
