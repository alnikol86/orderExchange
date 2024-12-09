package com.nikolaev.orderexchange.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PersonEntity {
    private Integer person_id;
    private String name;
    private String email;
    private String password;
    private Integer role;
    private Double rating;
}
