package com.nikolaev.orderexchange.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ReviewEntity {
    private Integer reviewId;
    private Integer projectId;
    private Integer reviewerId;
    private Integer reviewedPersonId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
