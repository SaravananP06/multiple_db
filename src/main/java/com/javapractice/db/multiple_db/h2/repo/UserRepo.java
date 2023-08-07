package com.javapractice.db.multiple_db.h2.repo;

import com.javapractice.db.multiple_db.h2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
