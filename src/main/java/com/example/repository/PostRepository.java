package com.example.repository;

import com.example.entity.BlogPost;
import com.example.entity.PostStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByStatus(PostStatus status, Pageable pageable);

    List<BlogPost> findAllByAuthor_Username(String username);

    Integer countArticleByAuthor(Long id);



}
