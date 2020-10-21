package com.example.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.twitter.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
	Tweet findByTitle(String title);
}
