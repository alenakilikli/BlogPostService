package com.example.controller;

import com.example.dto.postDTO.PostCreateRequestDTO;
import com.example.dto.postDTO.PostSearchRequestDTO;
import com.example.dto.postDTO.PostUpdateRequestDTO;
import com.example.dto.postDTO.PostCreateResponseDTO;
import com.example.dto.postDTO.PostResponseByIdDTO;
import com.example.dto.postDTO.PostSearchResponseDTO;
import com.example.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {


    private final PostService blogPostService;

    @GetMapping
    public List<PostSearchResponseDTO> showPosts() {
        return blogPostService.findLatestPosts();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponseDTO createPost(
            @RequestBody PostCreateRequestDTO requestDTO) {

        return blogPostService.create(requestDTO);
    }

    @PostMapping("/search")
    public List<PostSearchResponseDTO> searchPost(
            @RequestBody PostSearchRequestDTO requestDTO) {

        return blogPostService.searchPosts(requestDTO);
    }

    @GetMapping("/{id}")
    public PostResponseByIdDTO showPostsById(
            @PathVariable Long id) {

        return blogPostService.findPostById(id);
    }

    @PatchMapping("update/{id}")
    @PreAuthorize("hasAuthority('admin.posts.ro')||#id==authentication.principal.blogUser.id")
    public void updateArticle(
            @RequestBody PostUpdateRequestDTO updateRequestDTO, @PathVariable Long id) {

        blogPostService.update(updateRequestDTO, id);
    }

    @PutMapping("/publish/{id}")
    @PreAuthorize("hasAuthority('user.posts.rw')")
    public void setStatusPublish(
            @PathVariable Long id
    ) {
        blogPostService.setStatusPublished(id);
    }

    @PutMapping("/unpublish/{id}")
    @PreAuthorize("#id==authentication.principal.blogUser.id")
    public void setStatusUnpublish(
            @PathVariable Long id
    ) {
        blogPostService.setStatusUnpublished(id);
    }

    @PutMapping("/block/{id}")

    @PreAuthorize("hasAuthority('admin.posts.rw')")
    public void setStatusBlockOrPublish(
            @PathVariable Long id
    ) {
        blogPostService.setStatusBlockOrPublish(id);
    }

    @GetMapping("/username/{username}")
    public List<PostSearchResponseDTO> showPostByUser(
            @PathVariable String username
    ) {
        return blogPostService.showArticlesByUserName(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')||#id==authentication.principal.blogUser.id")
    @DeleteMapping("/delete/{id}")
    public void deleteArticle(
            @PathVariable Long id
          ) {

        blogPostService.deleteById(id);
    }

}
