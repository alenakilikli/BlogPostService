package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum RoleName {

    USER(1),
    ADMIN(2)
    ;

    private final Integer roleId;

    public static RoleName findBy(Integer id){
        if (id==null){
            return  null;
        }
        return Arrays.stream(RoleName.values())
                .filter(x-> Objects.equals(x.getRoleId(),id))
                .findFirst()
                .orElse(null);
    }

}
