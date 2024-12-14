package com.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
