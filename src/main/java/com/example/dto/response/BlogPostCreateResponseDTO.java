package com.example.dto.response;

import com.example.entity.Tag;
import com.example.entity.types.PostStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record BlogPostCreateResponseDTO(Long id,
                                        String title,
                                        String body,
                                        List<Tag> tag,
                                        PostStatus status,
                                        Long authorId) {



}
