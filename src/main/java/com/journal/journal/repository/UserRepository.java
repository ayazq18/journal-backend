package com.journal.journal.repository;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String UserName);
    @Transactional
    User deleteByUserName(String userName);
}
