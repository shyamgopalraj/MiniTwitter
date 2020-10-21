package com.example.twitter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.twitter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Page<User> findByUserId(Long userId,Pageable pageable);
	Page<User> findFollowersByUserId(Long userId,Pageable pageable);

}
