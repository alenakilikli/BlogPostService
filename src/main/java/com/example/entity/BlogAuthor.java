package com.example.entity;

import com.example.entity.converter.AccountStatusConverter;
import com.example.entity.role.Role;
import com.example.entity.types.AccountStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@ToString
@Entity
@Table(name = "blog_author")
@EntityListeners(AuditingEntityListener.class)
//@MappedSuperclass
public class BlogAuthor<U> extends AbstractEntity {
//    @CreatedBy
//    @Column(name = "updated_by")
//    private U updatedBy;
//
//    @LastModifiedBy
//    @Column(name = "created_by")
//    private U createdBy;

    @Column(name = "updated_on")
    @LastModifiedDate
    private Instant updatedOn;

    @Column(name = "created_on")
    @LastModifiedDate
    private Instant createdOn;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    @Convert(converter = AccountStatusConverter.class)
    private AccountStatus accountStatus;

    @Column(name = "roles")
    private Role role;

    @Column(name = "is_admin")
    private boolean isAdmin;


}
