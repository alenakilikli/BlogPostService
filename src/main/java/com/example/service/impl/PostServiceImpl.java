package com.example.service.impl;

import com.example.dto.request.PostCreateRequestDTO;
import com.example.dto.request.PostSearchRequestDTO;
import com.example.dto.request.PostUpdateRequestDTO;
import com.example.dto.response.BlogPostCreateResponseDTO;
import com.example.dto.response.BlogPostResponseByIdDTO;
import com.example.dto.response.BlogPostSearchResponseDTO;
import com.example.entity.*;
import com.example.repository.BlogAuthorRepository;
import com.example.repository.PostRepository;
import com.example.service.PostService;
import com.example.utils.BlogConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Transactional
@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BlogAuthorRepository blogAuthorRepository;


    @Override
    public List<BlogPostSearchResponseDTO> findLatestPosts() {

//        var status =
        var page = PageRequest.of(1, 30, Sort.by("createdOn").descending());
        return postRepository.findAllByStatus(PostStatus.PUBLISHED, page)
                .stream()
//        return postRepository.findAll().stream()
//                .sorted(Comparator.comparing(BlogPost::getCreatedOn).reversed())
//                .filter(status -> status.getStatus().equals(PostStatus.PUBLISHED))
//                .limit(30)
                .map(BlogConverter::mapToDto)
                .toList();
    }

    @Override
    public BlogPostCreateResponseDTO create(PostCreateRequestDTO postCreateRequestDTO) {

        BlogAuthor author = blogAuthorRepository
                .findById(postCreateRequestDTO.getAuthorId())
                .orElseThrow();

        if (author.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.UPGRADE_REQUIRED,
                    String.format("Blog with ID %d is in status [INACTIVE]", author.getId()));
        }

        BlogPost blogPost = BlogPost.builder()
                .title(postCreateRequestDTO.title)
                .body(postCreateRequestDTO.body)
                .tags(postCreateRequestDTO.tags)
                .status(PostStatus.PUBLISHED)
                .author(author)
                .createdOn(Instant.from(LocalDateTime.now()))
                .updatedOn(Instant.from(LocalDateTime.now())).build();

        return BlogConverter.mapToDtoCreate((postRepository.save(blogPost)));
    }

    // @PersistenceContext // == @Autowired for EntityManager
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BlogPostSearchResponseDTO> searchPosts(PostSearchRequestDTO searchRequestDTO) {
//        Map<Role,List<BlogPost>> posts = postRepository.findAll().stream().collect(Collectors.groupingBy(p->p.getAuthor().getRole()));
//        var user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user.equals(Role.ADMIN)){
//            return (List<BlogPostSearchResponseDTO>) posts.entrySet().stream().map(a->BlogConverter.mapToDtoListSearch(a.getValue()));
//        }



        return BlogConverter.mapToDtoListSearch(postRepository.findAll().stream()
                .filter(status -> status.getStatus().equals(PostStatus.PUBLISHED)).toList());
    }

    @Override
    public BlogPostResponseByIdDTO findPostById(Long id) {
        BlogPost post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return BlogConverter.mapToDtoById(post);
    }


    @Override
    public void update(PostUpdateRequestDTO updateRequestDTO, Long postId) {

        BlogPost blogPost = postRepository.findById(postId).orElseThrow();

        blogPost.setTitle(updateRequestDTO.title);
        blogPost.setBody(updateRequestDTO.body);
        blogPost.setTags(updateRequestDTO.tags);

        postRepository.save(blogPost);
    }

    @Override
    public void setStatusBlocked(Long id) {

        BlogPost post = postRepository.findById(id).orElseThrow();

        if (!post.getStatus().equals(PostStatus.BLOCKED)) {
            post.setStatus(PostStatus.BLOCKED);
        }

    }

    @Override
    public void setStatusUnpublished(Long id) {

        BlogPost post = postRepository.findById(id).orElseThrow();

        if (!post.getStatus().equals(PostStatus.UNPUBLISHED)) {
            post.setStatus(PostStatus.UNPUBLISHED);
        }

    }

    @Override
    public void setStatusPublished(Long id) {

        BlogPost post = postRepository.findById(id).orElseThrow();

        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            post.setStatus(PostStatus.PUBLISHED);
        }

    }

    @Override
    public List<BlogPostSearchResponseDTO> showArticlesByUserName(String name) {

        return postRepository.findAllByAuthor_Username(name)
                .stream()
                .map(BlogConverter::mapToDtoSearch)
                .toList();
    }

    @Override
    public void deleteById(long postId) {

        postRepository.deleteById(postId);
        log.info("Post with ID = {} deleted", postId);
    }


}