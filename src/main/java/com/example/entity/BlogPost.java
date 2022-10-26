package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity
@Table(name = "blog_post")
public class BlogPost extends AbstractEntity {


    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "blog_author_id")
    private BlogAuthor author;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private PostStatus status;

    @ElementCollection
    private Set<String> tags;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "created_on")
    @LastModifiedDate
    private Instant createdOn;



}
