package com.leoncarraro.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoncarraro.redditclone.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
