package com.example.twitter.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.twitter.model.User;
import com.example.twitter.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	private final static String USER_NOT_FOUND = "User does not exist";
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/user")
	public User postUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable (value = "userId") Long userId) {
		return userRepository.findById(userId).orElseThrow(RuntimeException::new);
	}
	
	@GetMapping("/followers/{userId}")
	public Page<User> getFollowerForUser(@PathVariable(value = "userId") Long userId, Pageable pageable) {
		return userRepository.findFollowersByUserId(userId, pageable);
	}
	
	@GetMapping("/following/{userId}")
	public Set<User> getFollowingForUser(@PathVariable(value = "userId") Long userId, Pageable pageable) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
		return user.getFollowing();
	}

	@PutMapping("/user/{userId}/follows/{followingId}")
	public User addFollowing(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "followingId") Long followingId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
		User userFollowing = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
		
		Set<User> following = user.getFollowing();
		following.add(userFollowing);
		user.setFollowing(following);
		
		Set<User> followers = userFollowing.getFollowers();
		followers.add(user);
		userFollowing.setFollowers(followers);
		
		return userRepository.save(user);
	}

}
