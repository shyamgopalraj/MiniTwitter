package com.example.twitter.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
@Getter @Setter @NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	//This is defining relation between followees and their followers
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_RELATIONS",
        joinColumns = @JoinColumn(name = "followed_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
	@JsonIgnore
	private Set<User> followers;
	
    @ManyToMany(mappedBy = "followers")
    @JsonIgnore
	private Set<User> following;
	
	//This is for defining relation between tweet and user
	@OneToMany(mappedBy = "tweeter")
	@JsonManagedReference
    private Set<Tweet> tweets;
	
	@NotBlank
	private String userName;
}
