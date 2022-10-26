package com.example.repository;

import com.example.entity.BlogAuthorSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogSessionRepository extends JpaRepository<BlogAuthorSession, Long> {

    BlogAuthorSession findBySessionId(String id);



}
