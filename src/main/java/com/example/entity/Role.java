package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

//@Entity
//@Table(name = "roles")
public class Role extends AbstractEntity{

    @Column(name = "role_name")
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    private List<BlogAuthor> authors;


}
