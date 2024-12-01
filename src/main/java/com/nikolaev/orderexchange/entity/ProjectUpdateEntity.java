package com.nikolaev.orderexchange.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProjectUpdateEntity {
    private Integer updateId;
    private Integer projectId;
    private String updateText;
    private LocalDateTime createdAt;
}
