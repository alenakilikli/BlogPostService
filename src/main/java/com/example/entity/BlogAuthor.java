package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "blog_author")
public class BlogAuthor extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Convert(converter = RoleConverter.class)
    @Column(name = "rolename")
    private RoleName roleName;

    @Column(name = "updated_on")
    @LastModifiedDate
    private Instant updatedOn;

    @Column(name = "created_on")
    @LastModifiedDate
    private Instant createdOn;

    @Column(name = "is_admin")
    private boolean isAdmin;

//    @ManyToMany
//    private List<Role> roles;

}
